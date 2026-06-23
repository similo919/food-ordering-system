# DAY 03 RESEARCH

## Q1. JPA vs Hibernate
JPA is a specification for working with databases in Java. Hibernate is the actual implementation of JPA. So Hibernate is what runs the JPA code.

---

## Q2. @Entity vs @Table
@Entity means the class is a database entity. @Table is used to define the table name in the database.

---

## Q3. Foreign Key and @ManyToOne
A foreign key links two tables. @ManyToOne means many records belong to one record.

Example:
Many menus belong to one category.

---

## Q4. @JoinColumn
It defines the column used as the foreign key in the database.

---

## Q5. Why BigDecimal for price
BigDecimal is used because it is accurate for money calculations. Double can cause rounding errors.

---

## Q6. LAZY vs EAGER
LAZY loads data only when needed. EAGER loads everything immediately. Default for @ManyToOne is EAGER.

---

## Q7. N+1 Problem
It happens when too many SQL queries are executed due to relationships between tables.

---

## Q8. Dependency Injection
It is a way of giving objects their dependencies instead of creating them manually.
Constructor injection is preferred because it is cleaner and easier to test.

---

## Q9. @RequiredArgsConstructor
It automatically creates a constructor for all final fields.

---

## Q10. Service Layer
It contains business logic. It separates logic from the controller to keep code clean.

---

## Q11. Why validate categoryId
Because a menu must belong to a valid category. If not, data becomes invalid.

---

## Q12. save() vs saveAndFlush()
save() saves data normally. saveAndFlush() saves immediately to the database.

---

## Q13. Why use mapper methods
To convert between entity and DTO and keep code clean and reusable.