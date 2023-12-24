package global.search.pretask.app

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import java.time.ZoneOffset
import java.util.*

@SpringBootApplication(
	scanBasePackages = [
		"global.search.pretask.app",
		"global.search.pretask.domain",
		"global.search.pretask.service",
		"global.search.pretask.persistence",
		"global.search.pretask.api",
	]
)
class AppApplication {

	@PostConstruct
	fun init() {
		// timezone - UTC로 현재 설정
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC.id))
	}
}

fun main(args: Array<String>) {
	runApplication<AppApplication>(*args)
}
