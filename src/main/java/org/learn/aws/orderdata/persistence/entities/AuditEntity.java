package org.learn.aws.orderdata.persistence.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "audit")
@NamedQuery(name = "listAllAudits", query = "select ae from AuditEntity ae")
public class AuditEntity {

    @Column(name = "row_id")
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "event")
    private String event;

    @Column(name = "host_name")
    private String hostName;

    @CreationTimestamp
    @Column(name = "create_date", columnDefinition = "TIMESTAMP")
    private ZonedDateTime createDate;

    public AuditEntity() {
    }

    public AuditEntity(String entityType, String entityId, String event) {
        this.entityType = entityType;
        this.entityId = entityId;
        this.event = event;
        this.hostName = System.getProperty("system.host.name", "NotAvailable");
    }

    public String getId() {
        return id;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public String getEvent() {
        return event;
    }

    public String getHostName() {
        return hostName;
    }
}
