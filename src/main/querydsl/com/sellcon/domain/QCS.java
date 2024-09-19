package com.sellcon.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCS is a Querydsl query type for CS
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCS extends EntityPathBase<CS> {

    private static final long serialVersionUID = 941694767L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCS cS = new QCS("cS");

    public final QAdmin admin;

    public final StringPath content = createString("content");

    public final NumberPath<Integer> cs_category = createNumber("cs_category", Integer.class);

    public final NumberPath<Long> csseq = createNumber("csseq", Long.class);

    public final DateTimePath<java.util.Date> regdate = createDateTime("regdate", java.util.Date.class);

    public final StringPath title = createString("title");

    public QCS(String variable) {
        this(CS.class, forVariable(variable), INITS);
    }

    public QCS(Path<? extends CS> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCS(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCS(PathMetadata metadata, PathInits inits) {
        this(CS.class, metadata, inits);
    }

    public QCS(Class<? extends CS> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.admin = inits.isInitialized("admin") ? new QAdmin(forProperty("admin")) : null;
    }

}

