# Backend CORS Fix Required

Since your frontend is now on Vercel (different domain than Render),
your Spring Boot / Node.js backend MUST allow cross-origin requests.

## If Spring Boot backend — add this to your main Application class or a config class:

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                "https://depra-ecom.onrender.com",
                "https://*.vercel.app",
                "http://localhost:3000",
                "http://localhost:8080"
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true);
    }
}
```

## If Node.js / Express backend — add this:

```js
const cors = require('cors');
app.use(cors({
  origin: [
    'https://depra-ecom.onrender.com',
    /\.vercel\.app$/,
    'http://localhost:3000'
  ],
  credentials: true
}));
```

## Image URLs in Database
If product images are stored as relative paths like `images/blue_Silk.jpeg`,
they resolve against your backend URL. Make sure your backend returns full URLs:
  ✅ https://depra-ecom.onrender.com/images/blue_Silk.jpeg
  ❌ /images/blue_Silk.jpeg  (breaks from Vercel)

Fix in your product API response:
```java
product.setImageUrl("https://depra-ecom.onrender.com/" + product.getImageUrl());
```
