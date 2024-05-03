package com.neupanesushant.kasthabackend.utils

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class SqlScriptInitializer {

    private var attemptCount = 0;

    @Autowired
    private lateinit var dataSource: DataSource

    @PostConstruct
    fun initData() {
        prePopulateData()
    }

    private fun prePopulateData() {
        try {
            val sqlResource = ClassPathResource("data.sql")
            ScriptUtils.executeSqlScript(dataSource.connection, sqlResource)
        } catch (e: Exception) {
            attemptCount++;
            if (attemptCount < 5)
                prePopulateData()
            else
                e.printStackTrace()
        }
    }
}
