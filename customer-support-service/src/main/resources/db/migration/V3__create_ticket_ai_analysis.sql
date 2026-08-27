CREATE TABLE ticket_ai_analysis (

    id BIGINT NOT NULL AUTO_INCREMENT,

    ticket_id BIGINT NOT NULL,

    intent VARCHAR(50) NOT NULL,

    sentiment VARCHAR(30) NOT NULL,

    urgency VARCHAR(30) NOT NULL,

    suggested_category VARCHAR(30) NOT NULL,

    suggested_priority VARCHAR(30) NOT NULL,

    ai_reasoning TEXT,

    analyzed_at DATETIME NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT uk_ticket_ai_analysis_ticket
        UNIQUE (ticket_id),

    CONSTRAINT fk_ticket_ai_analysis_ticket
        FOREIGN KEY (ticket_id)
        REFERENCES support_tickets(id)
);