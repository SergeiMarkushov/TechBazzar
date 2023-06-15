package ru.bazzar.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import java.util.concurrent.TimeUnit;
import com.google.common.cache.CacheBuilder;

@SpringBootApplication
@EnableCaching//главный рубильник кэша
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

//	Настройки кэширования
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
								.maximumSize(100000)
								//время жизни кэш-сущности
								.expireAfterWrite(2, TimeUnit.DAYS)
								.build().asMap(),
						false);
			}
		};
	}


}
