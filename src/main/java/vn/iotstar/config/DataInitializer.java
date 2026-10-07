package vn.iotstar.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.iotstar.entity.CategoryEntity;
import vn.iotstar.repository.CategoryRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(CategoryRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new CategoryEntity(null, "Điện thoại & Phụ kiện"));
                repository.save(new CategoryEntity(null, "Máy tính & Laptop"));
                repository.save(new CategoryEntity(null, "Thời trang nam"));
                repository.save(new CategoryEntity(null, "Thời trang nữ"));
                repository.save(new CategoryEntity(null, "Thiết bị điện tử"));
                repository.save(new CategoryEntity(null, "Đồng hồ thông minh"));
                repository.save(new CategoryEntity(null, "Sách & Văn phòng phẩm"));
                repository.save(new CategoryEntity(null, "Thể thao & Dã ngoại"));
                repository.save(new CategoryEntity(null, "Mẹ & Bé"));
                repository.save(new CategoryEntity(null, "Nhà cửa & Đời sống"));
            }
        };
    }

}
