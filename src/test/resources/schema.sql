CREATE SCHEMA global;

CREATE TABLE global.industries(
  industry_id bigserial primary key,
  industry_name varchar(255)
);

create schema crmuser;

CREATE TABLE crmuser.roles(
  role_id bigserial primary key,
  role varchar(20)
);

CREATE TABLE crmuser.accounts(
  account_id bigserial primary key,
  email varchar(500) unique,
  password varchar(250),
  first_name varchar(100),
  last_name varchar(100),
  is_enabled boolean,
  role_id bigint references crmuser.roles(role_id) on delete restrict
);

CREATE SCHEMA lead;

CREATE TABLE lead.comments(
  comment_id bigserial PRIMARY KEY,
  account_id bigint NOT NULL references crmuser.accounts(account_id),
  date_created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  message text
);

CREATE TABLE lead.statuses(
  status_id bigserial PRIMARY KEY,
  descriptor varchar(250)
);

CREATE TABLE lead.sources(
  source_id bigserial PRIMARY KEY,
  descriptor varchar(250)
);

CREATE TABLE lead.addresses(
  address_id bigserial PRIMARY KEY,
  apartment_number varchar(50),
  street varchar(250),
  city varchar(250),
  prov_state varchar(250),
  country varchar(250),
  zip_postal varchar(25),
  po_box varchar(25)
);

CREATE TABLE lead.emails(
  email_id bigserial PRIMARY KEY,
  email varchar(250)
);

CREATE TABLE lead.details(
  lead_id bigserial PRIMARY KEY,
  first_name varchar(250),
  last_name varchar(250) NOT NULL,
  phone varchar(30),
  mobile varchar(30),
  status_id bigint references lead.statuses(status_id) on update cascade on delete restrict,
  company_name varchar(250),
  title varchar(250),
  source_id bigint references lead.sources(source_id) on update cascade on delete restrict,
  industry_id bigint references global.industries(industry_id) on update cascade on delete restrict,
  twitter varchar(50),
  address_id bigint references lead.addresses(address_id) on delete restrict,
  primary_email_id bigint references lead.emails(email_id) on delete restrict,
  secondary_email_id bigint references lead.emails(email_id) on delete restrict,
  owner_id bigint references crmuser.accounts(account_id) on delete set null,
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified timestamp,
  last_modified_by_id bigint references crmuser.accounts(account_id) on delete set null
);

CREATE TABLE lead.comment_lookups(
  comment_id bigint references lead.comments(comment_id) on delete cascade,
  lead_id bigint references lead.details(lead_id) on delete cascade
);

create schema product;

CREATE TABLE product.categories(
  category_id bigserial PRIMARY KEY,
  descriptor varchar(100)
);

CREATE TABLE product.details(
    product_id bigserial PRIMARY KEY,
    code varchar(50),
    name varchar(250) NOT NULL,
    is_active boolean NOT NULL DEFAULT false,
    category_id bigint references product.categories(category_id),
    sales_start timestamp,
    sales_end timestamp,
    support_start timestamp,
    support_end timestamp,
    unit_price decimal,
    taxable bool,
    commission_rate_percent integer check(commission_rate_percent >= 0 AND commission_rate_percent <= 100),
    qty_in_stock bigint check(qty_in_stock >= 0),
    qty_in_order bigint check(qty_in_order >= 0),
    qty_in_demand bigint check(qty_in_demand >= 0),
    description text,
    created timestamp DEFAULT CURRENT_TIMESTAMP,
    last_modified timestamp DEFAULT CURRENT_TIMESTAMP,
    last_modified_by_id bigint references crmuser.accounts(account_id),
    owner_id bigint references crmuser.accounts(account_id)
);

CREATE SCHEMA contact;

CREATE SCHEMA account;

CREATE TABLE contact.emails(
  email_id bigserial PRIMARY KEY,
  email varchar(250)
);

CREATE TABLE contact.addresses(
  address_id bigserial PRIMARY KEY,
  apartment_number varchar(50),
  street varchar(250),
  city varchar(250),
  prov_state varchar(250),
  country varchar(250),
  zip_postal varchar(25),
  po_box varchar(25)
);

CREATE TABLE contact.comments(
  comment_id bigserial PRIMARY KEY,
  account_id bigint NOT NULL references crmuser.accounts(account_id) on delete set null,
  date_created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  message text
);

CREATE TABLE account.type(
  type_id bigserial PRIMARY KEY,
  descriptor varchar(100)
);

CREATE TABLE account.addresses(
  address_id bigserial PRIMARY KEY,
  apartment_number varchar(50),
  street varchar(250),
  city varchar(250),
  prov_state varchar(250),
  country varchar(250),
  zip_postal varchar(25),
  po_box varchar(25)
);

CREATE TABLE account.emails(
  email_id bigserial PRIMARY KEY,
  email varchar(250)
);


CREATE TABLE account.details(
  account_id bigserial PRIMARY KEY,
  name varchar(250) NOT NULL,
  rating float,
  phone varchar(30),
  other_phone varchar(30),
  fax varchar(30),
  parent_id bigint references account.details(account_id) on delete set null,
  type_id bigint references account.type(type_id) on delete restrict,
  ticker_symbol varchar(10),
  website varchar(100),
  number_of_employees integer check(number_of_employees > 0),
  billing_address_id bigint references account.addresses(address_id) on delete restrict,
  shipping_address_id bigint references account.addresses(address_id) on delete restrict,
  primary_email_id bigint references account.emails(email_id) on delete restrict,
  secondary_email_id bigint references account.emails(email_id) on delete restrict,
  owner_id bigint references crmuser.accounts(account_id) on delete set null,
  date_create timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified timestamp,
  last_modified_by_id bigint references crmuser.accounts(account_id) on delete set null
);

CREATE TABLE contact.details(
  contact_id bigserial PRIMARY KEY,
  first_name varchar(250),
  last_name varchar(250),
  account_id bigint references account.details(account_id) on delete set null,
  department varchar(250),
  phone varchar(30),
  other_phone varchar(30),
  mobile varchar(30),
  fax varchar(30),
  date_of_birth date,
  assistant_id bigint references contact.details(contact_id) on delete set null,
  title varchar(100),
  report_to_id bigint references contact.details(contact_id) on delete set null,
  skype varchar(250),
  twitter varchar(100),
  mailing_address_id bigint references contact.addresses(address_id) on delete restrict,
  other_address_id bigint references contact.addresses(address_id) on delete restrict,
  primary_email_id bigint references contact.emails(email_id) on delete restrict,
  secondary_email_id bigint references contact.emails(email_id) on delete restrict,
  owner_id bigint references crmuser.accounts(account_id) on delete set null,
  created_by bigint references crmuser.accounts(account_id) on delete set null,
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified timestamp,
  last_modified_by_id bigint references crmuser.accounts(account_id) on delete set null
);

CREATE TABLE contact.comment_lookups(
  comment_id bigint references contact.comments(comment_id) on delete cascade,
  contact_id bigint references contact.details(contact_id) on delete cascade,
  PRIMARY KEY(comment_id, contact_id)
);


CREATE SCHEMA ticket;

CREATE TABLE ticket.statuses(
  status_id bigserial PRIMARY KEY,
  descriptor varchar(250)
);

CREATE TABLE ticket.case_origins(
  origin_id bigserial PRIMARY KEY,
  descriptor varchar(250)
);

CREATE TABLE ticket.comments(
  comment_id bigserial PRIMARY KEY,
  account_id bigint NOT NULL references crmuser.accounts(account_id),
  date_created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  message text
);


CREATE TABLE ticket.details(
  ticket_id bigserial PRIMARY KEY,
  status_id bigint references ticket.statuses(status_id) on delete restrict,
  origin_id bigint references ticket.case_origins(origin_id) on delete restrict,
  contact_id bigint references contact.details(contact_id) on delete set null,
  phone varchar(30),
  product_id bigint references product.details(product_id) on delete set null,
  owner_id bigint references crmuser.accounts(account_id) on delete set null,
  subject varchar(100),
  account_id bigint references account.details(account_id) on delete set null,
  email varchar(250),
  description text,
  solution text
);

CREATE TABLE ticket.comment_lookups(
  comment_id bigint references ticket.comments(comment_id) on delete cascade,
  ticket_id bigint references ticket.details(ticket_id) on delete cascade,
  PRIMARY KEY(comment_id, ticket_id)
);

CREATE SCHEMA deal;

CREATE TABLE deal.types(
  type_id bigserial PRIMARY KEY,
  descriptor varchar(250)
);

CREATE TABLE deal.stages(
  stage_id bigserial PRIMARY KEY,
  descriptor varchar(250)
);

CREATE TABLE deal.comments(
  comment_id bigserial PRIMARY KEY,
  account_id bigint NOT NULL references crmuser.accounts(account_id) on delete set null,
  date_created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  message text
);

CREATE TABLE deal.details(
  deal_id bigserial PRIMARY KEY,
  owner_id bigint NOT NULL references crmuser.accounts(account_id) on delete set null,
  type_id bigint references deal.types(type_id) on delete restrict,
  stage_id bigint references deal.stages(stage_id) on delete restrict,
  expected_rev decimal,
  contact_id bigint references contact.details(contact_id) on delete set null,
  account_id bigint references account.details(account_id) on delete set null,
  amount_earned decimal,
  closing_date date,
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_by_id bigint references crmuser.accounts(account_id) on delete set null,
  last_modified timestamp,
  last_modified_by_id bigint references crmuser.accounts(account_id) on delete set null
);


CREATE TABLE deal.comment_lookups(
  comment_id bigint references deal.comments(comment_id),
  deal_id bigint references deal.details(deal_id),
  PRIMARY KEY(comment_id, deal_id)
);

CREATE SCHEMA sale;

CREATE TABLE sale.purchase_orders(
  purchase_order_id bigserial primary key,
  contact_id bigint references contact.details(contact_id) on delete set null,
  account_id bigint references account.details(account_id) on delete restrict,
  owner_id bigint references crmuser.accounts(account_id) on delete set null,
  created_by bigint references crmuser.accounts(account_id) on delete set null,
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified timestamp,
  last_modified_by_id bigint references crmuser.accounts(account_id) on delete set null
);

CREATE TABLE sale.purchase_product_lookups(
  purchase_product_id bigserial primary key,
  purchase_order_id bigint references sale.purchase_orders(purchase_order_id) on delete cascade,
  product_id bigint references product.details(product_id) on delete restrict,
  quantity bigint,
  discount numeric(5,5)
);

CREATE TABLE sale.quotes(
  quote_id bigserial primary key,
  account_id bigint references account.details(account_id) on delete restrict,
  contact_id bigint references contact.details(contact_id) on delete set null,
  owner_id bigint references crmuser.accounts(account_id) on delete set null,
  created_by bigint references crmuser.accounts(account_id) on delete set null,
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified timestamp,
  last_modified_by_id bigint references crmuser.accounts(account_id) on delete set null
);

CREATE TABLE sale.quote_product_lookup(
  quote_product_id bigserial primary key,
  product_id bigint references product.details(product_id) on delete restrict,
  quote_id bigint references sale.quotes(quote_id) on delete cascade,
  quantity bigint,
  discount numeric(5,5)
);

CREATE TABLE sale.addresses(
  address_id bigserial PRIMARY KEY,
  apartment_number varchar(50),
  street varchar(250),
  city varchar(250),
  prov_state varchar(250),
  country varchar(250),
  zip_postal varchar(25),
  po_box varchar(25)
);

CREATE TABLE sale.contract(
  contract_id bigserial primary key,
  contact_title varchar(50),
  contract text
);

CREATE TABLE sale.sales_orders(
  sale_order_id bigserial primary key,
  billing_address_id bigint references sale.addresses(address_id) on delete set null,
  shipping_address_id bigint references sale.addresses(address_id) on delete set null,
  contact_id bigint references contact.details(contact_id) on delete set null,
  account_id bigint references account.details(account_id) on delete restrict,
  quote_id bigint references sale.quotes(quote_id) on delete set null,
  tax_percent numeric(5,5),
  invoice_number bigint unique,
  contract_id bigint references sale.contract(contract_id) on delete restrict,
  owner_id bigint references crmuser.accounts(account_id) on delete set null,
  created_by bigint references crmuser.accounts(account_id) on delete set null,
  created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_modified timestamp,
  last_modified_by_id bigint references crmuser.accounts(account_id) on delete set null
);

CREATE TABLE sale.sale_product_lookups(
  sale_product_id bigserial primary key,
  sale_order_id bigint references sale.sales_orders(sale_order_id) on delete cascade,
  product_id bigint references product.details(product_id) on delete restrict,
  quantity bigint,
  discount numeric(5,5)
);

CREATE TABLE account.comments(
  comment_id bigserial PRIMARY KEY,
  date_created timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  message text
);

CREATE TABLE account.comment_lookups(
  comment_id bigint references account.comments(comment_id) on delete cascade,
  account_id bigint references account.details(account_id) on delete cascade,
  PRIMARY KEY(comment_id, account_id)
);

CREATE TABLE account.deal_lookups(
  deal_id bigint references deal.details(deal_id) on delete cascade,
  account_id bigint references account.details(account_id) on delete cascade,
  PRIMARY KEY(deal_id, account_id)
);

CREATE TABLE account.case_lookups(
  ticket_id bigint references ticket.details(ticket_id) on delete cascade,
  account_id bigint references account.details(account_id) on delete cascade,
  PRIMARY KEY(ticket_id, account_id)
);

CREATE TABLE account.contact_lookups(
  contact_id bigint references contact.details(contact_id) on delete cascade,
  account_id bigint references account.details(account_id) on delete cascade,
  PRIMARY KEY(contact_id, account_id)
);
