# 📚 Spring Library API 
This project is a wholy contenerized monolithic-style Library Management System built with **Spring Boot 3**. It includes endpoints to manage books, authors, and genres. The application uses **Spring Data JPA**, **Jakarta Validation**, and **MySQL** for data persistence and validation.

---

## 🚀 Launch Instructions

### Database
```bash
docker compose up
```

### Application
```bash
mvnw spring-boot:run
```

---

## 🛠️ Technologies Used

- **Spring Boot 3**
- **Spring Web**
- **Spring Data JPA**
- **MySQL**
- **Jakarta Bean Validation**
- **Docker & Docker Compose**
- **JUnit & Mockito**
- **Lombok** 

---

## 📘 Book

#### 🔹 `BookDto`
```json
{
  "id": 1,
  "title": "Clean Code",
  "publicationYear": 2008,
  "isbn": "9780132350884",
  "authorFirstname": "Robert",
  "authorLastname": "Martin",
  "genres": ["Programming", "Software Engineering"]
}
```

### Endpoints

**Base URL**: `http://localhost:8080`

#### 🔍 GET `/api/books`
Retrieve a list of all books.

#### 📘 GET `/api/books/{id}`
Get a specific book by ID.

#### ✍️ POST `/api/books`
Create a new book.

```json
{
  "title": "Clean Code",
  "isbn": "9780132350884",
  "publicationYear": 2008,
  "authorId": 1,
  "genreIds": [1, 2]
}
```

#### ✏️ PUT `/api/books/{id}`
Update an existing book.

```json
{
  "title": "Clean Coder",
  "isbn": "9780137081073",
  "publicationYear": 2011,
  "authorId": 1,
  "genreIds": [1]
}
```

#### ❌ DELETE `/api/books/{id}`
Delete a book by ID.

---

## 👤 Author

#### 🔹 `AuthorDto`
```json
{
  "id": 1,
  "firstname": "Robert",
  "lastname": "Martin",
  "books": [
    {
      "id": 1,
      "title": "Clean Code",
      "publicationYear": 2008,
      "isbn": "9780132350884",
      "genres": ["Programming"]
    }
  ]
}
```

### Endpoints

**Base URL**: `http://localhost:8080`

#### 🔍 GET `/api/authors`
Retrieve all authors.

#### 📘 GET `/api/authors/{id}`
Get a specific author by ID.

#### ✍️ POST `/api/authors`
Create a new author.

```json
{
  "firstname": "Robert",
  "lastname": "Martin"
}
```

#### ✏️ PUT `/api/authors/{id}`
Update an author.

```json
{
  "firstname": "Uncle",
  "lastname": "Bob"
}
```

#### ❌ DELETE `/api/authors/{id}`
Delete an author by ID.

---

## 🧪 Testing

Run unit tests:

```bash
./mvnw test
```

Coverage includes:
- Service Layer Tests
- Mapper Tests
