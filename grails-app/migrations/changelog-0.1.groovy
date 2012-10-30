databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1313695437667-1") {
        createTable(tableName: "open_id") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "open_idPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "url", type: "varchar(255)") {
                constraints(nullable: "false", unique: "true")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1313695437667-2") {
        createTable(tableName: "persistent_logins") {
            column(name: "series", type: "varchar(64)") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "persistent_loPK")
            }

            column(name: "last_used", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "token", type: "varchar(64)") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "varchar(64)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1313695437667-3") {
        createTable(tableName: "profile") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "profilePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "bio", type: "longtext") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false", unique: "true")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1313695437667-4") {
        createTable(tableName: "role") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "varchar(255)") {
                constraints(nullable: "false", unique: "true")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1313695437667-5") {
        createTable(tableName: "submission") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "submissionPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "accepted", type: "bit")

            column(name: "schedule", type: "datetime")

            column(name: "summary", type: "longtext") {
                constraints(nullable: "false")
            }

            column(name: "title", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1313695437667-6") {
        createTable(tableName: "user") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "account_expired", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "enabled", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "varchar(255)") {
                constraints(nullable: "false", unique: "true")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1313695437667-7") {
        createTable(tableName: "user_role") {
            column(name: "role_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1313695437667-8") {
        addPrimaryKey(columnNames: "role_id, user_id", constraintName: "user_rolePK", tableName: "user_role")
    }

    changeSet(author: "pledbrook (generated)", id: "1313695437667-10") {
        createIndex(indexName: "url_unique_1313695437474", tableName: "open_id", unique: "true") {
            column(name: "url")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1313695437667-12") {
        createIndex(indexName: "user_id_unique_1313695437507", tableName: "profile", unique: "true") {
            column(name: "user_id")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1313695437667-13") {
        createIndex(indexName: "authority_unique_1313695437514", tableName: "role", unique: "true") {
            column(name: "authority")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1313695437667-15") {
        createIndex(indexName: "username_unique_1313695437522", tableName: "user", unique: "true") {
            column(name: "username")
        }
    }
}
