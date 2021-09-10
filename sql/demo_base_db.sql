/*
 Navicat Premium Data Transfer

 Source Server         : Postgre
 Source Server Type    : PostgreSQL
 Source Server Version : 90101
 Source Host           : localhost:5432
 Source Catalog        : demo_base
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 90101
 File Encoding         : 65001

 Date: 10/09/2021 13:51:26
*/


-- ----------------------------
-- Sequence structure for tbl_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tbl_user_id_seq";
CREATE SEQUENCE "public"."tbl_user_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

-- ----------------------------
-- Table structure for tbl_company
-- ----------------------------
DROP TABLE IF EXISTS "public"."tbl_company";
CREATE TABLE "public"."tbl_company" (
  "id" int8 NOT NULL,
  "company_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;

-- ----------------------------
-- Table structure for tbl_job
-- ----------------------------
DROP TABLE IF EXISTS "public"."tbl_job";
CREATE TABLE "public"."tbl_job" (
  "id" int8 NOT NULL,
  "job_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "company_id" int8 NOT NULL
)
;

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."tbl_user";
CREATE TABLE "public"."tbl_user" (
  "id" int8 NOT NULL DEFAULT nextval('tbl_user_id_seq'::regclass),
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "address" varchar(255) COLLATE "pg_catalog"."default",
  "date_of_birth" timestamp(6)
)
;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."tbl_user_id_seq"
OWNED BY "public"."tbl_user"."id";
SELECT setval('"public"."tbl_user_id_seq"', 2, false);

-- ----------------------------
-- Primary Key structure for table tbl_company
-- ----------------------------
ALTER TABLE "public"."tbl_company" ADD CONSTRAINT "tbl_company_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tbl_job
-- ----------------------------
ALTER TABLE "public"."tbl_job" ADD CONSTRAINT "tbl_job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table tbl_user
-- ----------------------------
ALTER TABLE "public"."tbl_user" ADD CONSTRAINT "tbl_user_pkey" PRIMARY KEY ("id");
