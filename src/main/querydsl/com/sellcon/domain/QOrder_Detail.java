package com.sellcon.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder_Detail is a Querydsl query type for Order_Detail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder_Detail extends EntityPathBase<Order_Detail> {

    private static final long serialVersionUID = -138572127L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder_Detail order_Detail = new QOrder_Detail("order_Detail");

    public final NumberPath<Long> odseq = createNumber("odseq", Long.class);

    public final QOrders order;

    public final QSelling_Product selling_product;

    public QOrder_Detail(String variable) {
        this(Order_Detail.class, forVariable(variable), INITS);
    }

    public QOrder_Detail(Path<? extends Order_Detail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder_Detail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder_Detail(PathMetadata metadata, PathInits inits) {
        this(Order_Detail.class, metadata, inits);
    }

    public QOrder_Detail(Class<? extends Order_Detail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QOrders(forProperty("order"), inits.get("order")) : null;
        this.selling_product = inits.isInitialized("selling_product") ? new QSelling_Product(forProperty("selling_product"), inits.get("selling_product")) : null;
    }

}

