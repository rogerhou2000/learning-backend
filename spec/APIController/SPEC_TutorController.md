# 規格文件 - TutorController

來源檔案: `src/main/java/com/learning/api/controller/TutorController.java`

Base URL: `http://localhost:8080`

---

## 概述

管理 `Tutor` 實體的 CRUD 操作。`Tutor` 是對應老師使用者（`User.role=2`）的個人資料紀錄，其 `id` 與 `User.id` 相同。

---

## API 互動邏輯

### 1. 查詢老師資料

* **請求資訊（HTTP Request）**
- Method: `GET`
- URL: `/api/tutor/{id}`

| 參數 | 型別 | 說明 |
|---|---|---|
| `id` | Long | 老師的 User ID |

* **回應內容 (Response)**

| HTTP Status | 情境 | Body |
|---|---|---|
| `200 OK` | 查詢成功 | `Tutor` 物件（JSON） |
| `404 Not Found` | 查無老師資料 | `{ "msg": "查無老師資料" }` |

---

### 2. 建立老師資料

* **請求資訊（HTTP Request）**
- Method: `POST`
- URL: `/api/tutor`
- Headers: `Content-Type: application/json`
- Payload (Request Body):
```json
{
  "tutorId": 5
}
```

| 欄位 | 型別 | 必填 | 說明 |
|---|---|---|---|
| `tutorId` | Long | 是 | 老師的 User ID（需為 role=2 的使用者） |

* **回應內容 (Response)**

| HTTP Status | 情境 | Body |
|---|---|---|
| `200 OK` | 建立成功 | `{ "msg": "ok" }` |
| `400 Bad Request` | 建立失敗（使用者不存在、非老師角色、或已有 Tutor 紀錄） | `{ "msg": "建立失敗" }` |

---

### 3. 更新老師資料

* **請求資訊（HTTP Request）**
- Method: `PUT`
- URL: `/api/tutor/{id}`
- Headers: `Content-Type: application/json`
- Payload (Request Body): `TutorReq` 物件，可包含 title、intro 等欄位

| 參數 | 型別 | 說明 |
|---|---|---|
| `id` | Long | 老師的 User ID |

* **回應內容 (Response)**

| HTTP Status | 情境 | Body |
|---|---|---|
| `200 OK` | 更新成功 | `{ "msg": "ok" }` |
| `400 Bad Request` | 查無老師資料，更新失敗 | `{ "msg": "更新失敗" }` |

---

### 4. 刪除老師資料

* **請求資訊（HTTP Request）**
- Method: `DELETE`
- URL: `/api/tutor/{id}`

| 參數 | 型別 | 說明 |
|---|---|---|
| `id` | Long | 老師的 User ID |

* **回應內容 (Response)**

| HTTP Status | 情境 | Body |
|---|---|---|
| `200 OK` | 刪除成功 | `{ "msg": "ok" }` |
| `404 Not Found` | 查無老師資料 | `{ "msg": "查無老師資料" }` |

---

## 資料庫欄位說明

| 實體 | 資料表 | 主鍵 | 說明 |
|---|---|---|---|
| `Tutor` | `tutors` | `id`（= User.id） | 老師個人資料（title、intro、education、certificates 等） |
