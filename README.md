# BÀI TẬP 06: SPRING BOOT VỚI THYMELEAF, THYMELEAF LAYOUT DIALECT, CRUD VÀ TÌM KIẾM PHÂN TRANG CHO CATEGORY

**Môn học:** Lập trình Web (WEBPR330479)  
**Giảng viên hướng dẫn:** ThS. Nguyễn Hữu Trung  
**Sinh viên thực hiện:** Nguyễn Quang Vinh  
**MSSV:** 24133074  
**Trường:** Đại học Sư phạm Kỹ thuật TP.HCM (HCMUTE)  

---

## 1. Giới Thiệu Dự Án

Dự án được xây dựng bám sát 100% nội dung bài giảng slide **"Thymeleaf" (Từ Bước 1 đến Bước 8)**:
- **Spring Boot 3.3.4** và **Java 17**.
- **Thymeleaf Template Engine** kết hợp **Thymeleaf Layout Dialect** (`nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect`).
- Quản lý danh mục (**Category**) đầy đủ chức năng:
  - **C**reate: Thêm mới danh mục với kiểm tra hợp lệ dữ liệu (Bean Validation `@Valid`, `@NotEmpty`, `@Length`).
  - **R**ead: Hiển thị danh sách, xem chi tiết.
  - **U**pdate: Chỉnh sửa thông tin danh mục.
  - **D**elete: Xóa danh mục với hộp thoại Modal xác nhận an toàn (Bootstrap Modal).
  - **Search & Pagination**: Tìm kiếm từ khóa theo tên danh mục, phân trang linh hoạt (First, Previous, số trang, Next, Last) và tùy chọn kích thước trang (**Page Size: 3, 5, 10, 15, 20**).
- **Bố cục giao diện chuẩn:**
  - **Header:** Chứa thanh điều hướng (Navbar), chuyển đổi đa ngôn ngữ (i18n), cùng **hình ảnh chân dung cá nhân của sinh viên** và tên MSSV.
  - **Content:** Tái sử dụng layout chung qua `layout:fragment="content"`.
  - **Footer:** Chứa đầy đủ thông tin sinh viên thực hiện (Họ tên, MSSV, Khoa CNTT, Trường HCMUTE, môn học và GVHD).
- **Hỗ trợ đa ngôn ngữ (i18n):** Tiếng Việt (`vi_VN`) và Tiếng Anh (`en`) thông qua `LocaleResolver`, `LocaleChangeInterceptor` và các file `messages_*.properties`.

---

## 2. Cấu Trúc Thư Mục Dự Án

```
c:\Baitap06Laptrinhweb
├── pom.xml
├── run_app.bat
├── run_app.ps1
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── vn
    │   │       └── iotstar
    │   │           ├── Baitap06Application.java
    │   │           ├── config
    │   │           │   ├── DataInitializer.java (Khởi tạo dữ liệu mẫu)
    │   │           │   └── WebMvcConfig.java (Cấu hình i18n đa ngôn ngữ)
    │   │           ├── controller
    │   │           │   ├── HomeController.java (Redirect / sang quản lý danh mục)
    │   │           │   └── admin
    │   │           │       └── CategoryController.java (Controller CRUD, Search, Paging)
    │   │           ├── entity
    │   │           │   └── CategoryEntity.java (Entity bảng Categories)
    │   │           ├── model
    │   │           │   └── CategoryModel.java (DTO Form Model Validation)
    │   │           ├── repository
    │   │           │   └── CategoryRepository.java (Spring Data JPA Repository)
    │   │           └── service
    │   │               ├── ICategoryService.java (Interface nghiệp vụ)
    │   │               └── impl
    │   │                   └── CategoryServiceImpl.java (Triển khai nghiệp vụ)
    │   └── resources
    │       ├── application.properties
    │       ├── i18n
    │       │   ├── messages_en.properties (Ngôn ngữ Tiếng Anh)
    │       │   └── messages_vi.properties (Ngôn ngữ Tiếng Việt)
    │       ├── static
    │       │   └── images
    │       │       └── avatar.jpg (Hình ảnh cá nhân của sinh viên)
    │       └── templates
    │           └── admin
    │               ├── layout-admin.html (Master Layout Template)
    │               ├── fragments
    │               │   ├── header.html (Header chứa ảnh đại diện sinh viên)
    │               │   └── footer.html (Footer chứa thông tin sinh viên)
    │               └── categories
    │                   ├── addOrEdit.html (Form thêm mới / chỉnh sửa)
    │                   ├── list.html (Xem danh sách toàn bộ)
    │                   ├── search.html (Tìm kiếm đơn giản)
    │                   └── searchpaging.html (Tìm kiếm & Phân trang chuẩn slide)
```

---

## 3. Các Bước Triển Khai Theo Bài Giảng (Slide 20 - 35)

1. **Bước 1: Thêm dependency vào `pom.xml`**:
   - `spring-boot-starter-web`, `spring-boot-starter-thymeleaf`, `thymeleaf-layout-dialect`, `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`, `mssql-jdbc` & `mysql-connector-j`, `lombok`, `spring-boot-devtools`.
2. **Bước 2: Cấu hình CSDL & `application.properties`**:
   - Chạy trên cổng `server.port=8088`.
   - CSDL SQL Server: `localhost\SQLEXPRESS`, database `springboot_category`, tài khoản `sa` (có sẵn mẫu MySQL để chuyển đổi linh hoạt).
   - `spring.thymeleaf.cache=false` để cập nhật giao diện trực tiếp khi phát triển.
3. **Bước 3: Tạo Entity `CategoryEntity`**:
   - Khóa chính `@Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long categoryId`.
   - Thuộc tính `@Column(name = "category_name") String name`.
4. **Bước 4: Tầng Repository `CategoryRepository`**:
   - Kế thừa `JpaRepository<CategoryEntity, Long>`.
   - Các phương thức tìm kiếm: `findByNameContaining(String name)` và `findByNameContaining(String name, Pageable pageable)`.
5. **Bước 5: Tầng Service `ICategoryService` & `CategoryServiceImpl`**:
   - Xử lý các logic nghiệp vụ CRUD, đếm số lượng, phân trang `findAll(Pageable)`.
6. **Bước 6: Tầng Controller `CategoryController`**:
   - Định tuyến `/admin/categories`.
   - Hỗ trợ `/add`, `/saveOrUpdate`, `/edit/{categoryId}`, `/delete/{categoryId}`, `/search`, `/searchpaginated`.
7. **Bước 7: Thymeleaf Layout & Views**:
   - `layout-admin.html`: Bố cục chung tích hợp Header, Content và Footer.
   - `searchpaging.html`: Tìm kiếm kết hợp phân trang, chọn số bản ghi trên mỗi trang, popup modal xác nhận xóa danh mục.
   - `addOrEdit.html`: Biểu mẫu thêm/sửa có kiểm tra tính hợp lệ dữ liệu.
8. **Bước 8: Cấu hình đa ngôn ngữ i18n**:
   - `WebMvcConfig` với `CookieLocaleResolver` và `LocaleChangeInterceptor("language")`.
   - Chuyển đổi linh hoạt giữa Tiếng Việt (`?language=vi_VN`) và Tiếng Anh (`?language=en`).

---

## 4. Hướng Dẫn Khởi Động Dự Án

### Cách 1: Chạy bằng file script tiện lợi
- Double click chạy file `run_app.bat` hoặc mở PowerShell chạy:
  ```powershell
  .\run_app.ps1
  ```

### Cách 2: Chạy bằng lệnh Maven
```powershell
$env:JAVA_HOME = "C:\Java\jdk-17"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path
mvn spring-boot:run
```

Truy cập hệ thống tại: **[http://localhost:8088](http://localhost:8088)** hoặc **[http://localhost:8088/admin/categories/searchpaginated](http://localhost:8088/admin/categories/searchpaginated)**
