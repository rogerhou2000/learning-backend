# 測試規格文件 - TeacherCourseServiceTest

來源檔案: `src/test/java/com/learning/api/service/TeacherCourseServiceTest.java`

測試類型: 整合測試（`@SpringBootTest`，直接注入 Service）

---

## 概述

測試 `TeacherCourseService.addCourse()` 方法，驗證老師新增課程時的各種輸入驗證。

---

## 前置設定（@BeforeEach）

- 建立一個 role=2 的老師使用者。

---

## 測試方法一覽

**總計：5 個測試方法**

### addCourse（5 個測試）

| 測試方法 | 情境 | 預期結果 |
|---|---|---|
| `addCourse_validRequest_returnsTrue` | 完整正確的課程資料（tutorId、subject=11、price=500） | 回傳 `true` |
| `addCourse_nullTutorId_returnsFalse` | tutorId 為 null | 回傳 `false` |
| `addCourse_nullSubject_returnsFalse` | subject 為 null | 回傳 `false` |
| `addCourse_zeroPrice_returnsFalse` | price=0 | 回傳 `false` |
| `addCourse_longDescription_returnsFalse` | description 超過 1000 字元 | 回傳 `false` |
