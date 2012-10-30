databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1351588001223-1") {
        createTable(tableName: "mail_template") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "mail_templatePK")
            }

            column(name: "version", type: "bigint") { constraints(nullable: "false") }

            column(name: "template_key", type: "varchar(255)") { constraints(nullable: "false") }
            column(name: "to_address", type: "varchar(255)")
            column(name: "cc_address", type: "varchar(255)")
            column(name: "bcc_address", type: "varchar(255)")
            column(name: "subject", type: "varchar(255)") { constraints(nullable: "false") }
            column(name: "body", type: "longtext") { constraints(nullable: "false") }
        }
    }

}
