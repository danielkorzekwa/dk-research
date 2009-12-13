--
-- PostgreSQL database dump
--

-- Started on 2009-07-08 12:21:28

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 308 (class 2612 OID 16386)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--




SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1495 (class 1259 OID 16397)
-- Dependencies: 3
-- Name: market; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE market (
    exchange_id integer NOT NULL,
    market_id bigint NOT NULL,
    market_time timestamp without time zone NOT NULL,
    total_matched numeric NOT NULL
);


SET default_with_oids = true;

--
-- TOC entry 1496 (class 1259 OID 16416)
-- Dependencies: 3
-- Name: total_matched; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE total_matched (
    exchange_id integer NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    total_matched numeric NOT NULL
);


--
-- TOC entry 1775 (class 2606 OID 16404)
-- Dependencies: 1495 1495 1495
-- Name: market_pk; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY market
    ADD CONSTRAINT market_pk PRIMARY KEY (exchange_id, market_id);


--
-- TOC entry 1780 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2009-07-08 12:21:28

--
-- PostgreSQL database dump complete
--
 