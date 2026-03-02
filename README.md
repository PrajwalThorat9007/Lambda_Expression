# Lambda_Expression

## Validation Rules

### First Name and Last Name

| Rule | Valid | Invalid |
|------|-------|---------|
| Not null or empty | `Alice` | `null`, `""` |
| Alphabets only | `Smith` | `Alice123`, `Smith#`, `Ali ce` |

### Email — format: `abc.xyz@bl.co.in`

| Part | Rule |
|------|------|
| Local (`abc.xyz`) | 1 to 2 segments, atleast one letter per segment |
| `@` | exactly one |
| Domain (`bl.co.in`) | 2 to 3 segments, atleast one letter per segment |

| Valid | Invalid |
|-------|---------|
| `abc.xyz@bl.co.in` | `abcbl.co.in` — missing @ |
| `abc@bl.co` | `abc@@bl.co` — double @ |
| `abc@bl.co.in` | `abc@bl` — incomplete domain |
| `abc.xyz@bl.co` | `abc.@bl.co.in` — empty segment |
| | `123@456.78` — all numeric segments |

### Mobile — format: `91 9919819801`

| Part | Rule |
|------|------|
| Country Code | 1 to 3 digits, numeric only |
| Space | exactly one space |
| Number | exactly 10 digits, not starting with 0 |

### Password — 5 Rules

| Rule | Valid | Invalid |
|------|-------|---------|
| Min 8 characters | `Hello1@23` | `He1@` |
| Atleast one digit | `Hello1@23` | `HelloWorld@` |
| Atleast one uppercase | `Hello1@23` | `hello1@23` |
| Exactly one special char | `Hello1@23` | `Hello1@2#` |
| No spaces | `Hello1@23` | `Hello 1@2` |

Special characters: `!@#$%^&*()_+-=[]{}`

---

## Iteration History

### Iteration 1 — Streams, Boolean Return

Validation logic inside methods using Java Streams, returning `boolean`.

| Field | Stream Operation | Reason |
|-------|-----------------|--------|
| Name | `chars() + allMatch` | every char must be a letter |
| Email | `Stream.of() + count` | exactly 2 parts after @ |
| Email | `Stream.of() + allMatch` | every segment alphanumeric |
| Mobile | `Stream.of() + count` | exactly 2 parts after space |
| Password Rule 2 | `chars() + anyMatch` | stop at first digit found |
| Password Rule 3 | `chars() + anyMatch` | stop at first uppercase found |
| Password Rule 4 | `chars() + filter + count` | count all special chars |
| Password Rule 5 | `chars() + noneMatch` | stop at first space found |

---

### Iteration 2 — Custom Exceptions

Refactored from `boolean` return to throwing typed custom exceptions.

| Exception | Thrown when |
|-----------|------------|
| `InvalidFirstNameException` | null, empty, or non-alphabetic |
| `InvalidLastNameException` | null, empty, or non-alphabetic |
| `InvalidEmailException` | any email format rule violated |
| `InvalidMobileException` | any mobile format rule violated |
| `InvalidPasswordException` | any of the 5 password rules violated |

---

### Iteration 3 — Lambda Predicates

Each rule stored as a `Predicate<String>` lambda. Methods call `.test()` and throw if false.
```java
