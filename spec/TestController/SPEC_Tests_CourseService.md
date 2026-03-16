# 測試規格文件 - CourseServiceTest

來源檔案: `src/test/java/com/learning/api/service/CourseServiceTest.java`

測試類型: 整合測試（`@SpringBootTest`，直接注入 Service）

---

## 概述

測試 `CourseService` 的 `sendCourses()`、`getCourseById()`、`deleteById()` 方法。

---

## 前置設定（@BeforeEach）

建立以下測試資料：
- 老師使用者（role=2）
- 學生使用者（role=1）
- 已儲存的課程（price=500，subject=11，active=true）

---

## 測試方法一覽

**總計：9 個測試方法**

### sendCourses（5 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `sendCourses_validRequest_returnsTrue` | 完整正確的課程請求 | 回傳 `true` |
| `sendCourses_nullTutorId_returnsFalse` | tutorId 為 null | 回傳 `false` |
| `sendCourses_invalidSubject_returnsFalse` | subject=99（不合法） | 回傳 `false` |
| `sendCourses_zeroPrice_returnsFalse` | price=0 | 回傳 `false` |
| `sendCourses_nonTutorUser_returnsFalse` | 使用 role=1（學生）的 tutorId | 回傳 `false` |

### getCourseById（2 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `getCourseById_existingId_returnsCourseResp` | 查詢已存在的課程 | 回傳 `CourseResp`，id 與 name 正確 |
| `getCourseById_nonExistingId_returnsNull` | 查詢不存在的 ID | 回傳 `null` |

### deleteById（2 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `deleteById_existingId_returnsTrue` | 刪除已存在的課程 | 回傳 `true`，資料庫中不再存在 |
| `deleteById_nonExistingId_returnsFalse` | 刪除不存在的 ID | 回傳 `false` |
