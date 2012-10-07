databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1345661813822-2") {
        addColumn(tableName: "submission") {
            column(name: "year", type: "integer", valueNumeric: "2011") { constraints nullable: "false" }
        }
    }

    changeSet(author: "pledbrook", id: "ExpensesFieldsAdded") {
        addColumn(tableName: "profile") {
            column name: "need_travel", type: "boolean", valueBoolean: "false", { constraints nullable: "true" }
            column name: "need_accommodation", type: "boolean", valueBoolean: "false", { constraints nullable: "true" }
        }
    }

}
