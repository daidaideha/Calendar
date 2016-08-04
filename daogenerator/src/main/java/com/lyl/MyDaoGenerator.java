package com.lyl;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.lyl.calendar.dao");

        addOverTime(schema);
        new DaoGenerator().generateAll(schema, "../Calendar/app/src/main/java");
    }

    private static void addOverTime(Schema schema) {
        // 一个实体（类）就关联到数据库中的一张表，此处表名为「Note」（既类名）
        Entity overTime = schema.addEntity("OverTime");
        // 你也可以重新给表命名
        // note.setTableName("NODE");

        // greenDAO 会自动根据实体类的属性值来创建表字段，并赋予默认值
        // 接下来你便可以设置表中的字段：
        overTime.addIdProperty();
        overTime.addIntProperty("count").notNull();
        overTime.addIntProperty("job").notNull();
        overTime.addStringProperty("curMonth").notNull();
        // 与在 Java 中使用驼峰命名法不同，默认数据库中的命名是使用大写和下划线来分割单词的。
        // For example, a property called “creationDate” will become a database column “CREATION_DATE”.
        overTime.addDateProperty("startTime");
        overTime.addDateProperty("endTime");
        overTime.addDateProperty("date").notNull();
    }
}
