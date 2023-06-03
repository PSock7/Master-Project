const express = require("express");
const bcrypt = require("bcrypt");
const router = express.Router();
const dbConn = require("../config/dbConn");

// Route de création de compte
router.post("/signup", async (req, res) => {
  try {
    const { email, password, name, confirmPassword } = req.body;

    // Vérification du mot de passe confirmé
    if (password !== confirmPassword) {
      return res.status(400).json({ message: "Passwords do not match" });
    }

    // Hash du mot de passe
    const hashedPassword = await bcrypt.hash(password, 10);

    // Insertion des données dans la base de données
    const conn = await dbConn.getConnection();
    const result = await conn.execute(
      `INSERT INTO users (email, password, name) VALUES (:email, :password, :name)`,
      [email, hashedPassword, name]
    );

    // Fermeture de la connexion
    await conn.close();

    res.status(200).json({ message: "Account created successfully" });
  } catch (error) {
    console.error("Error creating account:", error);
    res.status(500).json({ message: "Internal Server Error" });
  }
});

// Route de connexion
router.post("/login", async (req, res) => {
  try {
    const { email, password } = req.body;

    // Récupération de l'utilisateur depuis la base de données
    const conn = await dbConn.getConnection();
    const result = await conn.execute(
      `SELECT * FROM users WHERE email = :email`,
      [email]
    );

    // Vérification de l'utilisateur
    if (result.rows.length === 0) {
      return res.status(400).json({ message: "Invalid credentials" });
    }

    const user = result.rows[0];

    // Vérification du mot de passe
    const passwordMatch = await bcrypt.compare(password, user.password);
    if (!passwordMatch) {
      return res.status(400).json({ message: "Invalid credentials" });
    }

    // TODO: Générer un jeton d'authentification (par exemple, JWT)

    res.status(200).json({ message: "Login successful" });
  } catch (error) {
    console.error("Error logging in:", error);
    res.status(500).json({ message: "Internal Server Error" });
  }
});

module.exports = router;
