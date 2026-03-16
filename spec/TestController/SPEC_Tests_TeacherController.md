# 測試規格文件 - TeacherControllerTest

來源檔案: `src/test/java/com/learning/api/controller/TeacherControllerTest.java`

測試類型: 整合測試（`@SpringBootTest` + MockMvc）

---

## 概述

測試 `TeacherController` 的 `/api/teacher/courses` 端點，驗證老師新增課程功能。

---

## 前置設定（@BeforeEach）

- 建立一個 role=2 的老師使用者。

---

## 測試方法一覽

**總計：4 個測試方法**

### POST /api/teacher/courses（4 個測試）

| 測試方法 | 情境 | 預期 HTTP 狀態碼 | 預期回應 |
|---|---|---|---|
| `createCourse_validRequest_shouldReturn200` | 完整且正確的課程資料 | `200 OK` | `{ "message": "課程新增成功！學生現在可以購買了！" }` |
| `createCourse_nullTutorId_shouldReturn400` | tutorId 為 null | `400 Bad Request` | `{ "message": "新增課程失敗，請檢查資料格式或價格" }` |
| `createCourse_zeroPrice_shouldReturn400` | price 為 0 | `400 Bad Request` | `{ "message": "新增課程失敗，請檢查資料格式或價格" }` |
| `createCourse_descriptionTooLong_shouldReturn400` | description 超過 1000 字元 | `400 Bad Request` | `{ "message": "新增課程失敗，請檢查資料格式或價格" }` |
