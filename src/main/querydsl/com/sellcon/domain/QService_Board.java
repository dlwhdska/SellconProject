package com.sellcon.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QService_Board is a Querydsl query type for Service_Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QService_Board extends EntityPathBase<Service_Board> {

    private static final long serialVersionUID = -516929411L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QService_Board service_Board = new QService_Board("service_Board");

    public final StringPath content = createString("content");

    public final QMember member;

    public final NumberPath<Long> qseq = createNumber("qseq", Long.class);

    public final DateTimePath<java.util.Date> regdate = createDateTime("regdate", java.util.Date.class);

    public final StringPath reply = createString("reply");

    public final NumberPath<Integer> repyn = createNumber("repyn", Integer.class);

    public final StringPath title = createString("title");

    public QService_Board(String variable) {
        this(Service_Board.class, forVariable(variable), INITS);
    }

    public QService_Board(Path<? extends Service_Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QService_Board(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QService_Board(PathMetadata metadata, PathInits inits) {
        this(Service_Board.class, metadata, inits);
    }

    public QService_Board(Class<? extends Service_Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

