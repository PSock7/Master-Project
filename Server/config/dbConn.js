const oracledb = require("oracledb");
require("dotenv").config();

async function initialize() {
  try {
    await oracledb.createPool({
      user: process.env.DB_USER,
      password: process.env.DB_PASSWORD,
      connectString: process.env.DB_CONNECT_STRING
    });

    console.log("Database connection pool created");

    // Test de connexion
    const connection = await oracledb.getConnection();
    console.log("Database connection established");

    // Libérer la connexion de test
    await connection.close();
    console.log("Database connection closed");
  } catch (error) {
    console.error("Error initializing database:", error);
    process.exit(1); // Quitte l'application en cas d'erreur
  }
}

module.exports.initialize = initialize;
module.exports.getConnection = () => oracledb.getConnection();
