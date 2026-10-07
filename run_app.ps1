$env:JAVA_HOME = "C:\Java\jdk-17"
$env:Path = "$env:JAVA_HOME\bin;" + $env:Path

Write-Host "======================================================================" -ForegroundColor Cyan
Write-Host "  KHỞI ĐỘNG DỰ ÁN SPRING BOOT BÀI TẬP 06 - THYMELEAF CRUD CATEGORY" -ForegroundColor Green
Write-Host "  Sinh viên: Nguyễn Quang Vinh - MSSV: 24133074" -ForegroundColor Yellow
Write-Host "  Khoa: CNTT - Trường: ĐH Sư phạm Kỹ thuật TP.HCM (HCMUTE)" -ForegroundColor White
Write-Host "  Đang mở ứng dụng tại: http://localhost:8088/admin/categories/searchpaginated" -ForegroundColor Cyan
Write-Host "======================================================================" -ForegroundColor Cyan

mvn spring-boot:run
