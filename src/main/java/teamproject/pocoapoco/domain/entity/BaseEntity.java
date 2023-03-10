package teamproject.pocoapoco.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString
public class BaseEntity{

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime lastModifiedAt;


    private LocalDateTime deletedAt;

    // 삭제
    public void deleteSoftly(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    // 확인
    public boolean isSoftDeleted() {
        return null != deletedAt;
    }
    // 삭제 취소
    public void undoDeletion(){
        this.deletedAt = null;
    }
}
