# Music Recommendation Engine 🎵

## 🎯 Goal

This repository contains my first Machine Learning project. The primary objective is to learn and implement a complete data engineering lifecycle from raw data ingestion to feature engineering and dynamic model adjustment.

## 🎧 Project Overview

A content-based recommendation system designed to find and suggest similar musical tracks based on a single input song or a cluster of songs. The system calculates the mathematical distance between audio features to identify the closest match.

## 💻 Tech Stack

* **Language:** Python 3.x
* **Data Manipulation & Engineering:** `pandas`
* **Machine Learning:** `scikit-learn` (K-Nearest Neighbors)
* **Database & Metadata Storage:** `sqlite3`
* **Environment:** macOS / VS Code

## ⚙️ Data Pipeline

The system operates on a linear pipeline, separated into modular scripts to ensure data integrity:

1. **Get Data:** Ingestion of raw Spotify audio features and metadata.
2. **Clean Data (`scripts/01-cleaning.py`):** * Normalization of continuous variables.
* Categorical mapping (reducing hundreds of genres into 15).
* Handling missing values and ensuring type consistency.


3. **Prepare for Machine (`scripts/02-cleaning.py`):**
* **Min-Max Scaling:** Applied to continuous numerical features to prevent magnitude bias in distance calculations.
* **One-Hot Encoding:** Applied to distinct categorical variables to prevent false mathematical proximity.
* **Feature Isolation:** Stripping all string/text metadata to export a pure, mathematical matrix (`data/dataset_for_machine.csv`).


4. **Apply Weights Matrix:** Processing the feature matrix through a K-Nearest Neighbors (KNN) algorithm to calculate Euclidean distances between tracks.
5. **Adjust Matrix with User Feedback (Dynamic Loop):** Implementing a reinforcement layer where user interactions (likes/skips) penalize or boost specific feature weights.

## 🗄️ Data Architecture & Storage

* **The Storage Layer (`scripts/03-database.py`):** the cleaned dataset is stripped from the dataset and stored in a relational **SQLite** database.
* **The Bridge:** The unique `track_id` serves as the `PRIMARY KEY`. Once the algorithm identifies the closest numerical vector, the system queries the SQLite database via the `track_id` to retrieve the readable track information.

## 🚧 Current Status & Next Steps

* [x] Data Cleaning & Mapping
* [x] Feature Normalization & Encoding
* [x] SQLite Schema Design & Data Migration
* [ ] Implementation of KNN Recommender Script
* [ ] Development of the Dynamic Weighting Matrix based on synthetic feedback loops