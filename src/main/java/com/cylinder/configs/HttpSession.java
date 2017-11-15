package com.cylinder.configs;

import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

/**
* @author Ryan Piper
* This just allows http sessions with a h2 database backend.
*/
@EnableJdbcHttpSession
public class HttpSession{}
