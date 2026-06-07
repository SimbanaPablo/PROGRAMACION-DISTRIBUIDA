import {useState} from 'react';
import './App.css';
import axios from "axios";

interface Author {
    id: number;
    name: string;
    version: number;
}

interface Book {
    isbn: string;
    price: number;
    title: string;
    authors: Array<Author>;
    version: number;
}


function App() {

    const [authors, setAuthors] = useState<Author[]>([]);
    const [books, setBooks] = useState<Book[]>([]);

    const handleClickAuthors = () => {
        axios
            .get<Author[]>("http://localhost/app-authors/authors")
            .then(response => {
                setAuthors(response.data);
            })
            .catch(error => {
                alert(error);
            });
    };

    const handleClickBooks = () => {
        axios
            .get<Book[]>("http://localhost/app-books/books")
            .then(response => {
                setBooks(response.data);
            })
            .catch(error => {
                alert(error);
            });
    };


    return (
        <>
            <section id="center">
                <div>
                    <h2>Authors</h2>
                </div>

                <button onClick={handleClickAuthors}>
                    Consultar
                </button>
                <br/>
                {
                    authors.map(author => (
                        <p key={author.id}>
                            {author.id} - {author.name} - {author.version}
                        </p>
                    ))
                }
            </section>

            <br/>

            <section id="center">
                <div>
                    <h2>Books</h2>
                </div>

                <button onClick={handleClickBooks}>
                    Consultar
                </button>
                <br/>
                {
                    books.map(book =>
                        <ul key={book.isbn}>
                            <li>{book.isbn} - {book.title} - ${book.price} - {book.version}</li>
                            {
                                book.authors.map(author =>
                                    <span>
                                        {author.name}
                                        <br/>
                                    </span>
                                )
                            }
                        </ul>
                    )
                }
            </section>
        </>
    );
}

export default App;