package com.myblog.search;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.myblog.support.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TrendingIT extends IntegrationTest {
    @Autowired
    TrendingService trending;

    /** 인기 검색어 집계 SQL이 실제 DB에서 돌아간다 ("AND" 뒤 공백이 빠지면 문법 오류). */
    @Test
    void refreshRunsAgainstTheDatabase() {
        assertThatCode(trending::refresh).doesNotThrowAnyException();
    }
}
