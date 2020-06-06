CREATE TABLE "transport_system"."intercity"(
    "company_name" varchar(100) ,
    "place_type" varchar(100) ,
    "transport_type" varchar(100) ,
    "dep_city" varchar(100) ,
    "ticket_num" int ,
    "dest_city" varchar(100) ,
    "dep_time" timestamp with time zone ,
    "price" varchar(100) ,
    "tax_rate" varchar(100)
);

CREATE TABLE "transport_system"."intercity_discount"(
    "passenger_type" varchar(100) ,
    "company_name" varchar(100) ,
    "discount" varchar(100)
);