# 測試規格文件 - AuthControllerTest

來源檔案: `src/test/java/com/learning/api/controller/AuthControllerTest.java`

測試類型: 整合測試（`@SpringBootTest` + MockMvc）

---

## 概述

測試 `AuthController` 的 `/api/auth/register` 與 `/api/auth/login` 端點，驗證輸入驗證、業務邏輯及 HTTP 狀態碼。

---

## 前置設定（@BeforeEach）

- 建立測試使用者（email: `authtest@example.com`，密碼已 BCrypt 加密），供登入測試使用。

---

## 測試方法一覽

**總計：8 個測試方法**

### POST /api/auth/register（5 個測試）

| 測試方法 | 情境 | 預期 HTTP 狀態碼 | 預期回應 |
|---|---|---|---|
| `register_validRequest_shouldReturn200` | 正確的新使用者資料 | `200 OK` | `{ "msg": "註冊成功" }` |
| `register_duplicateEmail_shouldReturn400` | 使用已存在的 email | `400 Bad Request` | `{ "msg": "此 email 已被註冊" }` |
| `register_blankName_shouldReturn400` | name 為空白字串 | `400 Bad Request` | — |
| `register_shortPassword_shouldReturn400` | 密碼少於 8 字元（7 字元） | `400 Bad Request` | — |
| `register_invalidEmail_shouldReturn400` | email 格式不正確（非 email 格式） | `400 Bad Request` | — |

### POST /api/auth/login（3 個測試）

| 測試方法 | 情境 | 預期 HTTP 狀態碼 | 預期回應 |
|---|---|---|---|
| `login_validCredentials_shouldReturn200WithToken` | 正確 email 與密碼 | `200 OK` | `{ "token": "<非空 JWT>" }` |
| `login_wrongPassword_shouldReturn400` | 正確 email 但密碼錯誤 | `400 Bad Request` | `{ "msg": "帳號或密碼錯誤" }` |
| `login_nonExistentUser_shouldReturn400` | 不存在的 email | `400 Bad Request` | `{ "msg": "帳號或密碼錯誤" }` |
