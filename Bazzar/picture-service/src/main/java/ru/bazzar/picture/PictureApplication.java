package ru.bazzar.picture;


import com.google.common.cache.CacheBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableCaching//главный рубильник кэша
public class PictureApplication {

	public static void main(String[] args) {
		SpringApplication.run(PictureApplication.class, args);
	}
	//Настройки кэширования
	@Bean("CacheManager")
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager() {
			@Override
			protected Cache createConcurrentMapCache(String name) {
				return new ConcurrentMapCache(
						name,
						//тут регулируется нагрузка
						CacheBuilder.newBuilder()
								//max размер кэша
								.maximumSize(10000)
								//время жизни кэш-сущности
								.expireAfterWrite(1, TimeUnit.DAYS)
								.build().asMap(),
						false);
			}
		};
	}

}
