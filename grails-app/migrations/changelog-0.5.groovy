databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1352102120402-1") {
        createTable(tableName: "talk_assignment") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "talk_assignmePK")
            }

            column(name: "version", type: "bigint") { constraints nullable: "false" }
            column(name: "talk_id", type: "bigint") { constraints nullable: "false" }
            column(name: "track_id", type: "bigint") { constraints nullable: "false" }
            column(name: "slot_id", type: "bigint") { constraints nullable: "false" }
        }

        
    }

    changeSet(author: "pledbrook (generated)", id: "1352102120402-2") {
        createTable(tableName: "time_slot") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "time_slotPK")
            }

            column(name: "version", type: "bigint") { constraints nullable: "false" }
            column(name: "day", type: "integer") { constraints nullable: "false" }
            column(name: "slot_position", type: "integer") { constraints nullable: "false" }
            column(name: "start_time", type: "datetime") { constraints nullable: "false" }
            column(name: "end_time", type: "datetime") { constraints nullable: "false" }
        }

        createIndex(indexName: "talk_id_unique_1352102120172", tableName: "talk_assignment", unique: "true") {
            column(name: "talk_id")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1352102120402-3") {
        createTable(tableName: "track") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "trackPK")
            }

            column(name: "version", type: "bigint") { constraints nullable: "false" }
            column(name: "name", type: "varchar(255)") { constraints nullable: "false" }
            column(name: "location", type: "varchar(255)")
            column(name: "track_position", type: "integer") { constraints nullable: "false" }
        }
    }

    changeSet(author: "pledbrook", id: "1352102120402-4") {
        addForeignKeyConstraint(
                baseColumnNames: "talk_id",
                baseTableName: "talk_assignment",
                constraintName: "FKA1064440E5E53A8B",
                deferrable: "false",
                initiallyDeferred: "false",
                referencedColumnNames: "id",
                referencedTableName: "submission",
                referencesUniqueColumn: "false")
        addForeignKeyConstraint(
                baseColumnNames: "track_id",
                baseTableName: "talk_assignment",
                constraintName: "FKA1064440F135CD89",
                deferrable: "false",
                initiallyDeferred: "false",
                referencedColumnNames: "id",
                referencedTableName: "track",
                referencesUniqueColumn: "false")
        addForeignKeyConstraint(
                baseColumnNames: "slot_id",
                baseTableName: "talk_assignment",
                constraintName: "FKA10644403FF4AB58",
                deferrable: "false",
                initiallyDeferred: "false",
                referencedColumnNames: "id",
                referencedTableName: "time_slot",
                referencesUniqueColumn: "false")
    }
}
