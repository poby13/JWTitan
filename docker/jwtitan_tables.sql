-- 1. 사용자 및 시스템 권한 관련 테이블
-- 사용자 테이블
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 시스템 역할 테이블
CREATE TABLE system_roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,    -- SUPER_ADMIN, ORGANIZATION_ADMIN, EVENT_MANAGER, USER, GUEST
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 사용자-시스템 역할 매핑 테이블
CREATE TABLE user_system_roles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    role_id BIGINT NOT NULL REFERENCES system_roles(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, role_id)
);

-- 2. 조직 관련 테이블
-- 조직 테이블
CREATE TABLE organizations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    logo_url VARCHAR(255),
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 조직 멤버십 테이블 (JSONB 활용)
CREATE TABLE organization_members (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    roles JSONB NOT NULL,  -- OWNER, ADMIN, MANAGER, MEMBER, GUEST
    primary_role VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(organization_id, user_id)
);

-- 3. 이벤트 관련 테이블
-- 이벤트 테이블
CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id),
    title VARCHAR(200) NOT NULL,
    description TEXT,
    event_type VARCHAR(50) NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    registration_start_date TIMESTAMP NOT NULL,
    registration_end_date TIMESTAMP NOT NULL,
    max_participants INTEGER,
    location_type VARCHAR(20) NOT NULL,
    location_details JSONB,
    fee DECIMAL(10,2),
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
    created_by BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 이벤트 등록 테이블
CREATE TABLE event_registrations (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL REFERENCES events(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    registration_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',  -- PENDING, APPROVED, REJECTED, CANCELED
    payment_status VARCHAR(20),  -- UNPAID, PAID, REFUNDED
    payment_amount DECIMAL(10,2),
    payment_date TIMESTAMP,
    additional_info JSONB,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(event_id, user_id)
);

-- 이벤트 참여 테이블
CREATE TABLE event_participations (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL REFERENCES events(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    participation_type VARCHAR(20) NOT NULL,  -- ORGANIZER, STAFF, PARTICIPANT, OBSERVER
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    registration_id BIGINT REFERENCES event_registrations(id),
    assigned_by BIGINT NOT NULL REFERENCES users(id),
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(event_id, user_id, participation_type)
);

-- 4. 권한 관련 테이블
-- 권한 정의 테이블
CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 역할-권한 매핑 테이블
CREATE TABLE role_permissions (
    id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    permission_id BIGINT NOT NULL REFERENCES permissions(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(role_name, permission_id)
);

-- 이벤트 역할별 권한 테이블
CREATE TABLE event_role_permissions (
    id BIGSERIAL PRIMARY KEY,
    participation_type VARCHAR(20) NOT NULL,  -- ORGANIZER, STAFF, PARTICIPANT, OBSERVER
    permission_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(participation_type, permission_name)
);

-- 5. 이력 관리 테이블
-- 역할 변경 이력 테이블
CREATE TABLE role_change_history (
    id BIGSERIAL PRIMARY KEY,
    entity_type VARCHAR(20) NOT NULL,  -- ORGANIZATION or EVENT
    entity_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id),
    old_roles JSONB,
    new_roles JSONB,
    changed_by BIGINT NOT NULL REFERENCES users(id),
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 이벤트 참여 이력 테이블
CREATE TABLE event_participation_history (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL REFERENCES events(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    action VARCHAR(50) NOT NULL,  -- ROLE_ASSIGNED, ROLE_REMOVED, STATUS_CHANGED
    old_type VARCHAR(20),
    new_type VARCHAR(20),
    old_status VARCHAR(20),
    new_status VARCHAR(20),
    changed_by BIGINT NOT NULL REFERENCES users(id),
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 6. 인덱스 생성
CREATE INDEX idx_org_members_org_id ON organization_members(organization_id);
CREATE INDEX idx_org_members_user_id ON organization_members(user_id);
CREATE INDEX idx_org_members_status ON organization_members(status);
CREATE INDEX idx_org_members_roles ON organization_members USING gin (roles);

CREATE INDEX idx_events_org_id ON events(organization_id);
CREATE INDEX idx_events_status ON events(status);
CREATE INDEX idx_events_dates ON events(start_date, end_date);

CREATE INDEX idx_event_participations_event ON event_participations(event_id);
CREATE INDEX idx_event_participations_user ON event_participations(user_id);
CREATE INDEX idx_event_participations_type ON event_participations(participation_type);

CREATE INDEX idx_event_registrations_event ON event_registrations(event_id);
CREATE INDEX idx_event_registrations_user ON event_registrations(user_id);
CREATE INDEX idx_event_registrations_status ON event_registrations(registration_status);

CREATE INDEX idx_role_history_entity ON role_change_history(entity_type, entity_id);
CREATE INDEX idx_role_history_user ON role_change_history(user_id);

CREATE INDEX idx_participation_history_event ON event_participation_history(event_id);
CREATE INDEX idx_participation_history_user ON event_participation_history(user_id);



-- 이전 스키마의 모든 테이블들은 유지하고, 아래의 테이블들을 추가

-- 1. 이벤트 카테고리 관련
-- 이벤트 카테고리 테이블
CREATE TABLE event_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT true,
    display_order INTEGER,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 이벤트-카테고리 매핑 테이블
CREATE TABLE event_category_mappings (
    event_id BIGINT NOT NULL REFERENCES events(id),
    category_id BIGINT NOT NULL REFERENCES event_categories(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (event_id, category_id)
);

-- 2. 이벤트 결제 관련
-- 결제 상세 테이블
CREATE TABLE event_payments (
    id BIGSERIAL PRIMARY KEY,
    registration_id BIGINT NOT NULL REFERENCES event_registrations(id),
    payment_method VARCHAR(50) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,  -- PENDING, COMPLETED, FAILED, CANCELLED
    payment_key VARCHAR(100),     -- 외부 결제 시스템의 키
    payment_provider VARCHAR(50), -- 결제 제공자 (예: STRIPE, PAYPAL)
    payment_date TIMESTAMP,
    refund_status VARCHAR(20),    -- NULL, REQUESTED, COMPLETED, REJECTED
    refund_amount DECIMAL(10,2),
    refund_reason TEXT,
    refund_date TIMESTAMP,
    meta_data JSONB,             -- 결제 관련 추가 정보
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 3. 이벤트 후기 관련
-- 이벤트 후기 테이블
CREATE TABLE event_reviews (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL REFERENCES events(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',  -- ACTIVE, HIDDEN, DELETED
    is_anonymous BOOLEAN NOT NULL DEFAULT false,
    images JSONB,  -- 후기 이미지 URL 배열
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(event_id, user_id)
);

-- 후기 좋아요 테이블
CREATE TABLE event_review_likes (
    review_id BIGINT NOT NULL REFERENCES event_reviews(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (review_id, user_id)
);

-- 4. 알림 관련
-- 알림 설정 테이블
CREATE TABLE notification_settings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    notification_type VARCHAR(50) NOT NULL,  -- EMAIL, SMS, PUSH
    is_enabled BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, notification_type)
);

-- 이벤트 알림 구독 테이블
CREATE TABLE event_notification_subscriptions (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT NOT NULL REFERENCES events(id),
    user_id BIGINT NOT NULL REFERENCES users(id),
    subscription_type VARCHAR(50) NOT NULL,  -- ALL, UPDATES, CHAT, EMERGENCY
    is_enabled BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(event_id, user_id, subscription_type)
);

-- 알림 이력 테이블
CREATE TABLE notification_history (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    event_id BIGINT REFERENCES events(id),
    notification_type VARCHAR(50) NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'SENT',  -- SENT, DELIVERED, READ, FAILED
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP,
    meta_data JSONB
);

-- 5. 첨부파일 관련
-- 첨부파일 테이블
CREATE TABLE attachments (
    id BIGSERIAL PRIMARY KEY,
    entity_type VARCHAR(50) NOT NULL,  -- EVENT, REVIEW
    entity_id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(100) NOT NULL,
    file_size BIGINT NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    storage_provider VARCHAR(50) NOT NULL,  -- S3, GOOGLE_CLOUD, etc.
    storage_path VARCHAR(500) NOT NULL,
    is_public BOOLEAN NOT NULL DEFAULT false,
    uploaded_by BIGINT NOT NULL REFERENCES users(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 추가 인덱스 생성
CREATE INDEX idx_event_categories_name ON event_categories(name);
CREATE INDEX idx_event_categories_active ON event_categories(is_active);
CREATE INDEX idx_event_payments_registration ON event_payments(registration_id);
CREATE INDEX idx_event_payments_status ON event_payments(status);
CREATE INDEX idx_event_reviews_event ON event_reviews(event_id);
CREATE INDEX idx_event_reviews_user ON event_reviews(user_id);
CREATE INDEX idx_event_reviews_rating ON event_reviews(rating);
CREATE INDEX idx_notifications_user ON notification_history(user_id);
CREATE INDEX idx_notifications_event ON notification_history(event_id);
CREATE INDEX idx_attachments_entity ON attachments(entity_type, entity_id);
CREATE INDEX idx_attachments_uploaded_by ON attachments(uploaded_by);

-- 뷰 생성 (자주 사용되는 쿼리를 위한)
CREATE VIEW event_summary_view AS
SELECT
    e.id AS event_id,
    e.title,
    e.start_date,
    e.end_date,
    e.status,
    o.name AS organization_name,
    COUNT(DISTINCT ep.id) FILTER (WHERE ep.participation_type = 'PARTICIPANT') AS participant_count,
    COUNT(DISTINCT er.id) FILTER (WHERE er.registration_status = 'APPROVED') AS approved_registrations,
    COALESCE(AVG(erv.rating), 0) AS average_rating,
    COUNT(DISTINCT erv.id) AS review_count
FROM events e
LEFT JOIN organizations o ON e.organization_id = o.id
LEFT JOIN event_participations ep ON e.id = ep.event_id
LEFT JOIN event_registrations er ON e.id = er.event_id
LEFT JOIN event_reviews erv ON e.id = erv.event_id
GROUP BY e.id, o.name;