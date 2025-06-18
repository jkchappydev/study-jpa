package org.example.dialect;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

public class MySQL5Dialect extends MySQL5InnoDBDialect {

    public MySQL5Dialect() {
        // group_concat 라는 이름을 가진 사용자 정의 함수 등록
        registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }

}
