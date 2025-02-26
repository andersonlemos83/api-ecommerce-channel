db = db.getSiblingDB("admin");

db.createUser({
    user: "admin",
    pwd: "secret",
    roles: [
        { role: "root", db: "admin" }
    ]
});

db = db.getSiblingDB("ecommerce_db");

db.createUser({
    user: "ecommerce_user",
    pwd: "ecommerce_user",
    roles: [
        { role: "root", db: "ecommerce_db" }
    ]
});

print("Banco de dados e usuários criados com sucesso!");