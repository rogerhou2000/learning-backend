# 規格文件 - AuthController

來源檔案: `src/main/java/com/learning/api/controller/AuthController.java`

Base URL: `http://localhost:8080`

---

## 概述

處理使用者身份驗證的 REST API，包含註冊與登入功能。

- 註冊委派給 `MemberService.register()`
- 登入委派給 `AuthService.loginReq()`，回傳 JWT token

---

## API 互動邏輯

### 1. 會員註冊

* **請求資訊（HTTP Request）**
- Method: `POST`
- URL: `/api/auth/register`
- Headers: `Content-Type: application/json`
- Payload (Request Body):
```json
{
  "name": "王小明",
  "email": "user@example.com",
  "password": "securePassword123",
  "role": 1,
  "birthday": "1990-01-01"
}
```

| 欄位 | 型別 | 必填 | 說明 |
|---|---|---|---|
| `name` | String | 是 | 姓名，不可為空白 |
| `email` | String | 是 | 電子郵件，須唯一，須符合 email 格式 |
| `password` | String | 是 | 密碼，最少 8 字元 |
| `role` | Integer | 是 | 角色（1=學生，2=老師，3=管理員） |
| `birthday` | LocalDate | 否 | 生日（格式：YYYY-MM-DD） |

* **回應內容 (Response)**
- HTTP Status: `200 OK`（成功）
- Body:
```json
{
  "msg": "註冊成功"
}
```
- HTTP Status: `400 Bad Request`（例如 email 重複或驗證失敗）
- Body:
```json
{
  "msg": "（錯誤原因）"
}
```

---

### 2. 會員登入

* **請求資訊（HTTP Request）**
- Method: `POST`
- URL: `/api/auth/login`
- Headers: `Content-Type: application/json`
- Payload (Request Body):
```json
{
  "email": "user@example.com",
  "password": "securePassword123"
}
```

| 欄位 | 型別 | 必填 | 說明 |
|---|---|---|---|
| `email` | String | 是 | 電子郵件 |
| `password` | String | 是 | 密碼 |

* **回應內容 (Response)**
- HTTP Status: `200 OK`（成功）
- Body: JWT token 物件（由 `AuthService.loginReq()` 回傳）
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
- HTTP Status: `400 Bad Request`（帳號或密碼錯誤）
- Body:
```json
{
  "msg": "帳號或密碼錯誤"
}
```

---

## 依賴服務

| 服務 | 職責 |
|---|---|
| `MemberService` | 處理使用者註冊（email 唯一性驗證、BCrypt 密碼雜湊、儲存 User） |
| `AuthService` | 處理登入驗證（密碼比對、JWT 產生） |

---

## 資料庫欄位說明

| 實體 | 資料表 | 主鍵 | 重要欄位 |
|---|---|---|---|
| `User` | `users` | `id` (Long, 自動生成) | `email` (不可為空, 唯一)、`password` (BCrypt 雜湊)、`role` (不可為空)、`wallet` (預設 0) |
