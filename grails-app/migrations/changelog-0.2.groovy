databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1345661813822-1") {
        createTable(tableName: "oauth_id") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints nullable: "false", primaryKey: "true", primaryKeyName: "oauth_idPK"
            }

            column(name: "version", type: "bigint") { constraints(nullable: "false") }
            column(name: "access_token", type: "varchar(255)") { constraints(nullable: "false") }
            column(name: "provider", type: "varchar(255)") { constraints(nullable: "false") }
            column(name: "user_id", type: "bigint") { constraints(nullable: "false") }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1345661813822-3") {
        addForeignKeyConstraint(
                baseColumnNames: "user_id", baseTableName: "oauth_id",
                constraintName: "FKE41947C3D1F846CF", deferrable: "false",
                initiallyDeferred: "false", referencedColumnNames: "id",
                referencedTableName: "user", referencesUniqueColumn: "false")
        addForeignKeyConstraint(
                baseColumnNames: "user_id", baseTableName: "open_id",
                constraintName: "FKB4B52F30D1F846CF", deferrable: "false",
                initiallyDeferred: "false", referencedColumnNames: "id",
                referencedTableName: "user", referencesUniqueColumn: "false")
        addForeignKeyConstraint(
                baseColumnNames: "user_id", baseTableName: "profile",
                constraintName: "FKED8E89A9D1F846CF", deferrable: "false",
                initiallyDeferred: "false", referencedColumnNames: "id",
                referencedTableName: "user", referencesUniqueColumn: "false")
        addForeignKeyConstraint(
                baseColumnNames: "user_id", baseTableName: "submission",
                constraintName: "FK84363B4CD1F846CF", deferrable: "false",
                initiallyDeferred: "false", referencedColumnNames: "id",
                referencedTableName: "user", referencesUniqueColumn: "false")
        addForeignKeyConstraint(
                baseColumnNames: "role_id", baseTableName: "user_role",
                constraintName: "FK143BF46A2CCD82EF", deferrable: "false",
                initiallyDeferred: "false", referencedColumnNames: "id",
                referencedTableName: "role", referencesUniqueColumn: "false")
        addForeignKeyConstraint(
                baseColumnNames: "user_id", baseTableName: "user_role",
                constraintName: "FK143BF46AD1F846CF", deferrable: "false",
                initiallyDeferred: "false", referencedColumnNames: "id",
                referencedTableName: "user", referencesUniqueColumn: "false")

        createIndex(indexName: "identity_idx", tableName: "oauth_id") {
            column name: "access_token"
            column name: "provider"
        }
    }
}
