# 測試規格文件 - TutorServiceTest

來源檔案: `src/test/java/com/learning/api/service/TutorServiceTest.java`

測試類型: 整合測試（`@SpringBootTest`，直接注入 Service）

---

## 概述

測試 `TutorService` 的 `getTutor()`、`createTutor()`、`updateTutor()`、`deleteTutor()` 方法。

---

## 前置設定（@BeforeEach）

建立以下測試資料：
- **既有老師**（role=2，已有 Tutor 紀錄，title="Math Teacher"）
- **新老師**（role=2，尚未建立 Tutor 紀錄）
- **學生**（role=1）

---

## 測試方法一覽

**總計：9 個測試方法**

### getTutor（2 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `getTutor_existingId_returnsTutor` | 查詢有 Tutor 紀錄的老師 | 回傳 `Tutor` 物件，id 正確 |
| `getTutor_nonExistingId_returnsNull` | 查詢不存在的 ID | 回傳 `null` |

### createTutor（5 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `createTutor_validTutorUser_returnsTrue` | role=2 使用者，尚未有 Tutor 紀錄 | 回傳 `true`，資料庫新建紀錄 |
| `createTutor_nullTutorId_returnsFalse` | tutorId 為 null | 回傳 `false` |
| `createTutor_nonExistentUser_returnsFalse` | 不存在的 tutorId | 回傳 `false` |
| `createTutor_nonTutorRole_returnsFalse` | role=1（學生）的使用者 | 回傳 `false` |
| `createTutor_duplicate_returnsFalse` | 已有 Tutor 紀錄的老師重複建立 | 回傳 `false` |

### updateTutor（2 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `updateTutor_existingId_returnsTrue` | 更新已存在的 Tutor 紀錄（title） | 回傳 `true`，資料庫中 title 更新正確 |
| `updateTutor_nonExistingId_returnsFalse` | 更新不存在的 ID | 回傳 `false` |

### deleteTutor（2 個測試，但與 create 共享，實際合計 9 個）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `deleteTutor_existingId_returnsTrue` | 刪除已存在的 Tutor 紀錄 | 回傳 `true`，資料庫中不再存在 |
| `deleteTutor_nonExistingId_returnsFalse` | 刪除不存在的 ID | 回傳 `false` |
