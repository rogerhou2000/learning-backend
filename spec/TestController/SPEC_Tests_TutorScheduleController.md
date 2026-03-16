# 測試規格文件 - TutorScheduleControllerTest

來源檔案: `src/test/java/com/learning/api/controller/TutorScheduleControllerTest.java`

測試類型: 整合測試（`@SpringBootTest` + MockMvc）

---

## 概述

測試 `TutorScheduleController` 的 `/api/teacher/schedules` 端點，驗證老師切換排班狀態與查詢週課表功能。

---

## 前置設定（@BeforeEach）

- 建立一個 role=2 的老師使用者。

---

## 測試方法一覽

**總計：6 個測試方法**

### POST /api/teacher/schedules/toggle（4 個測試）

| 測試方法 | 情境 | 預期 HTTP 狀態碼 | 預期回應 |
|---|---|---|---|
| `toggleSlot_setAvailable_noExisting_shouldReturn200` | 設為 available，無既有紀錄（新建） | `200 OK` | `{ "msg": "時段狀態已更新" }` |
| `toggleSlot_setInactive_existingRecord_shouldReturn200` | 設為 inactive，有既有 available 紀錄（刪除） | `200 OK` | `{ "msg": "時段狀態已更新" }` |
| `toggleSlot_invalidHour_shouldReturn400` | hour=8（小於最小值 9） | `400 Bad Request` | `{ "msg": "（非空錯誤訊息）" }` |
| `toggleSlot_invalidWeekday_shouldReturn400` | weekday=0（小於最小值 1） | `400 Bad Request` | `{ "msg": "（非空錯誤訊息）" }` |

### GET /api/teacher/schedules/{tutorId}（2 個測試）

| 測試方法 | 情境 | 預期 HTTP 狀態碼 | 預期回應 |
|---|---|---|---|
| `getSchedule_withRecords_shouldReturnList` | 有課表紀錄 | `200 OK` | 陣列長度 >= 1 |
| `getSchedule_noRecords_shouldReturnEmptyList` | 無課表紀錄 | `200 OK` | 空陣列 `[]` |
