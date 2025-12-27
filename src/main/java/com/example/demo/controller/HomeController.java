package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String home() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Vendor Compliance API</title>
                    <style>
                        * {
                            margin: 0;
                            padding: 0;
                            box-sizing: border-box;
                        }
                        body {
                            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
                            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                            min-height: 100vh;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            padding: 20px;
                        }
                        .container {
                            background: white;
                            border-radius: 20px;
                            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
                            padding: 60px 40px;
                            max-width: 600px;
                            width: 100%;
                            text-align: center;
                        }
                        h1 {
                            color: #333;
                            font-size: 2.5rem;
                            margin-bottom: 10px;
                            font-weight: 700;
                        }
                        .subtitle {
                            color: #666;
                            font-size: 1.1rem;
                            margin-bottom: 40px;
                        }
                        .status {
                            display: inline-block;
                            background: #10b981;
                            color: white;
                            padding: 8px 20px;
                            border-radius: 20px;
                            font-weight: 600;
                            margin-bottom: 30px;
                            font-size: 0.9rem;
                        }
                        .links {
                            display: flex;
                            flex-direction: column;
                            gap: 15px;
                            margin-top: 30px;
                        }
                        .btn {
                            display: block;
                            padding: 15px 30px;
                            text-decoration: none;
                            border-radius: 10px;
                            font-weight: 600;
                            font-size: 1rem;
                            transition: all 0.3s ease;
                        }
                        .btn-primary {
                            background: #667eea;
                            color: white;
                        }
                        .btn-primary:hover {
                            background: #5568d3;
                            transform: translateY(-2px);
                            box-shadow: 0 10px 20px rgba(102, 126, 234, 0.4);
                        }
                        .btn-secondary {
                            background: #f3f4f6;
                            color: #333;
                        }
                        .btn-secondary:hover {
                            background: #e5e7eb;
                            transform: translateY(-2px);
                        }
                        .info {
                            margin-top: 40px;
                            padding-top: 30px;
                            border-top: 1px solid #e5e7eb;
                        }
                        .info-item {
                            display: flex;
                            justify-content: space-between;
                            padding: 10px 0;
                            color: #666;
                            font-size: 0.9rem;
                        }
                        .info-label {
                            font-weight: 600;
                        }
                        @media (max-width: 600px) {
                            h1 {
                                font-size: 2rem;
                            }
                            .container {
                                padding: 40px 30px;
                            }
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="status">🟢 API Running</div>
                        <h1>Vendor Compliance API</h1>
                        <p class="subtitle">RESTful API for managing vendor compliance and documentation</p>
                        
                        <div class="links">
                            <a href="/swagger-ui.html" class="btn btn-primary">
                                📚 Open API Documentation (Swagger UI)
                            </a>
                            <a href="/v3/api-docs" class="btn btn-secondary">
                                📄 View OpenAPI Specification
                            </a>
                        </div>
                        
                        <div class="info">
                            <div class="info-item">
                                <span class="info-label">Version:</span>
                                <span>1.0.0</span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Framework:</span>
                                <span>Spring Boot 3</span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Base URL:</span>
                                <span>/api</span>
                            </div>
                            <div class="info-item">
                                <span class="info-label">Authentication:</span>
                                <span>JWT Bearer Token</span>
                            </div>
                        </div>
                    </div>
                </body>
                </html>
                """;
    }
}