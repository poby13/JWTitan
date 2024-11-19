-- 1. 스키마 생성
CREATE SCHEMA IF NOT EXISTS jwtitan_schema;

-- 2. 스키마 권한 설정
GRANT USAGE ON SCHEMA jwtitan_schema TO poby13;
GRANT CREATE ON SCHEMA jwtitan_schema TO poby13;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA jwtitan_schema TO poby13;
ALTER DEFAULT PRIVILEGES IN SCHEMA jwtitan_schema GRANT ALL ON TABLES TO poby13;

-- 3. 검색 경로 설정
ALTER USER poby13 SET search_path TO jwtitan_schema, public;

-- 4. 테이블 생성 (jwtitan_schema 스키마 사용)
CREATE TABLE jwtitan_schema.users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN DEFAULT true
);