-- Run once in MySQL before first application start (Flyway manages tables inside yx_circle).
CREATE DATABASE IF NOT EXISTS yx_circle
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
