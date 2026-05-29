import {useState} from 'react';
import './App.css';
import axios from "axios";

interface Author {
    id: number;
    nombre: string;
    version: number;
}

interface Book {
    isbn: string;
    price: number;
    title: string;
    author: Array<Author>;
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
                            {author.id} - {author.nombre} - {author.version}
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
                    books.map(author => (
                        <p key={author.isbn}>
                            {author.isbn} - {author.title} - {author.price} - {author.version}
                        </p>
                    ))
                }
            </section>
        </>
    );
}

export default App;