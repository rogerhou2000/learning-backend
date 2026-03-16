# 測試規格文件 - TutorScheduleServiceTest

來源檔案: `src/test/java/com/learning/api/service/TutorScheduleServiceTest.java`

測試類型: 整合測試（`@SpringBootTest`，直接注入 Service）

---

## 概述

測試 `TutorScheduleService` 的 `toggleSchedule()` 與 `getWeeklySchedule()` 方法，驗證排班切換邏輯。

---

## 前置設定（@BeforeEach）

- 建立一個 role=2 的老師使用者。

---

## 測試方法一覽

**總計：5 個測試方法**

### toggleSchedule（4 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `toggleSchedule_available_noExisting_createsRecord` | 目標 available，無既有紀錄 | 回傳 `"success"`，資料庫新建紀錄 |
| `toggleSchedule_inactive_existingRecord_deletesRecord` | 目標 inactive，有既有 available 紀錄 | 回傳 `"success"`，資料庫紀錄被刪除 |
| `toggleSchedule_invalidHour_returnsErrorMessage` | hour=8（小於最小值 9） | 回傳包含 `"格式錯誤"` 的字串 |
| `toggleSchedule_invalidWeekday_returnsErrorMessage` | weekday=0（小於最小值 1） | 回傳包含 `"格式錯誤"` 的字串 |

### getWeeklySchedule（1 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `getWeeklySchedule_returnsAvailableSlots` | 老師有 2 筆課表紀錄（weekday=3/h=9，weekday=5/h=15） | 回傳 `List` 長度等於 2 |
