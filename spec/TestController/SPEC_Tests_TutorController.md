# 測試規格文件 - TutorControllerTest

來源檔案: `src/test/java/com/learning/api/controller/TutorControllerTest.java`

測試類型: 整合測試（`@SpringBootTest` + MockMvc）

---

## 概述

測試 `TutorController` 的 `/api/tutor` 端點，驗證老師資料的 CRUD 操作。

---

## 前置設定（@BeforeEach）

建立以下測試資料：
- **既有老師**（role=2，已有 Tutor 紀錄，title="Math Teacher"）
- **新老師**（role=2，尚未建立 Tutor 紀錄）
- **學生**（role=1）

---

## 測試方法一覽

**總計：9 個測試方法**

### GET /api/tutor/{id}（2 個測試）

| 測試方法 | 情境 | 預期 HTTP 狀態碼 | 預期回應 |
|---|---|---|---|
| `getTutor_existingId_shouldReturn200` | 查詢已存在的老師 | `200 OK` | Tutor 物件（含 `id`） |
| `getTutor_nonExistingId_shouldReturn404` | 查詢不存在的 ID (999999) | `404 Not Found` | `{ "msg": "查無老師資料" }` |

### POST /api/tutor（4 個測試）

| 測試方法 | 情境 | 預期 HTTP 狀態碼 | 預期回應 |
|---|---|---|---|
| `createTutor_validRequest_shouldReturn200` | 合法的 role=2 使用者，尚未有 Tutor 紀錄 | `200 OK` | `{ "msg": "ok" }` |
| `createTutor_nonExistentUser_shouldReturn400` | 不存在的使用者 ID | `400 Bad Request` | `{ "msg": "建立失敗" }` |
| `createTutor_nonTutorRole_shouldReturn400` | 使用 role=1（學生）的使用者 ID | `400 Bad Request` | `{ "msg": "建立失敗" }` |
| `createTutor_duplicate_shouldReturn400` | 已有 Tutor 紀錄的老師重複建立 | `400 Bad Request` | `{ "msg": "建立失敗" }` |

### PUT /api/tutor/{id}（2 個測試）

| 測試方法 | 情境 | 預期 HTTP 狀態碼 | 預期回應 |
|---|---|---|---|
| `updateTutor_existingId_shouldReturn200` | 更新已存在的老師 | `200 OK` | `{ "msg": "ok" }` |
| `updateTutor_nonExistingId_shouldReturn400` | 更新不存在的 ID | `400 Bad Request` | `{ "msg": "更新失敗" }` |

### DELETE /api/tutor/{id}（2 個測試，但 deleteTutor_existingId + nonExistingId = 2，加上前述共 9 個測試的實際分布）

| 測試方法 | 情境 | 預期 HTTP 狀態碼 | 預期回應 |
|---|---|---|---|
| `deleteTutor_existingId_shouldReturn200` | 刪除已存在的老師 | `200 OK` | `{ "msg": "ok" }` |
| `deleteTutor_nonExistingId_shouldReturn404` | 刪除不存在的 ID | `404 Not Found` | `{ "msg": "查無老師資料" }` |
