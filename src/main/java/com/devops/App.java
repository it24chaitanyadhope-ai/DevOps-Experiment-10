package com.devops;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {

    public static void main(String[] args) throws IOException {

        int port = 8081;

        HttpServer server = HttpServer.create(
                new InetSocketAddress(port), 0
        );

        server.createContext("/", App::handleRequest);

        server.setExecutor(null);
        server.start();

        System.out.println(
                "CampusFind is running at http://localhost:" + port
        );
    }

    private static void handleRequest(HttpExchange exchange)
            throws IOException {

        String response = """
                <!DOCTYPE html>
                <html lang="en">

                <head>

                    <meta charset="UTF-8">

                    <meta name="viewport"
                          content="width=device-width, initial-scale=1.0">

                    <title>CampusFind - Lost & Found</title>

                    <style>

                        * {
                            box-sizing: border-box;
                            margin: 0;
                            padding: 0;
                        }

                        body {
                            font-family: Arial, sans-serif;
                            background: #f4f6f8;
                            color: #222;
                            min-height: 100vh;
                        }


                        /* NAVBAR */

                        nav {
                            background: #263b5b;
                            color: white;
                            padding: 18px 8%;
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                        }

                        .logo {
                            font-size: 24px;
                            font-weight: bold;
                        }

                        nav a {
                            color: white;
                            text-decoration: none;
                            margin-left: 25px;
                            font-size: 15px;
                            cursor: pointer;
                        }

                        nav a:hover {
                            text-decoration: underline;
                        }


                        /* PAGE SYSTEM */

                        .page {
                            display: none;
                        }

                        .page.active {
                            display: block;
                        }


                        /* HOME */

                        .hero {
                            background: #dfeaf5;
                            min-height: calc(100vh - 69px);
                            padding: 80px 8%;
                            display: flex;
                            flex-direction: column;
                            justify-content: center;
                            align-items: center;
                            text-align: center;
                        }

                        .hero h1 {
                            font-size: 42px;
                            margin-bottom: 15px;
                            color: #263b5b;
                        }

                        .hero p {
                            font-size: 18px;
                            margin-bottom: 30px;
                            color: #555;
                        }

                        .hero button {
                            background: #263b5b;
                            color: white;
                            border: none;
                            padding: 13px 25px;
                            border-radius: 5px;
                            cursor: pointer;
                            font-size: 15px;
                        }

                        .hero button:hover {
                            background: #1c2d46;
                        }


                        /* COMMON CONTAINER */

                        .container {
                            width: 84%;
                            max-width: 1100px;
                            margin: 0 auto;
                            padding: 45px 0;
                            min-height: calc(100vh - 69px);
                        }

                        .section-title {
                            text-align: center;
                            margin-bottom: 25px;
                            color: #263b5b;
                        }


                        /* SEARCH */

                        .search-box {
                            background: white;
                            padding: 25px;
                            border-radius: 8px;
                            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
                            margin-bottom: 40px;
                        }

                        .search-row {
                            display: flex;
                            gap: 12px;
                            flex-wrap: wrap;
                        }

                        input,
                        select,
                        textarea {
                            padding: 12px;
                            border: 1px solid #ccc;
                            border-radius: 5px;
                            font-size: 14px;
                        }

                        #searchInput {
                            flex: 1;
                            min-width: 220px;
                        }

                        #searchCategory {
                            min-width: 170px;
                        }

                        button {
                            background: #3d6f9e;
                            color: white;
                            border: none;
                            padding: 12px 20px;
                            border-radius: 5px;
                            cursor: pointer;
                        }

                        button:hover {
                            background: #315d85;
                        }

                        #searchResult {
                            margin-top: 15px;
                        }


                        /* ITEM CARDS */

                        .items {
                            display: grid;
                            grid-template-columns: repeat(3, 1fr);
                            gap: 20px;
                        }

                        .item-card {
                            background: white;
                            padding: 22px;
                            border-radius: 8px;
                            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
                        }

                        .item-card h3 {
                            margin-bottom: 10px;
                            color: #263b5b;
                        }

                        .item-card p {
                            color: #666;
                            margin: 6px 0;
                            font-size: 14px;
                        }

                        .tag {
                            display: inline-block;
                            background: #e4edf7;
                            color: #315d85;
                            padding: 5px 9px;
                            border-radius: 12px;
                            font-size: 12px;
                            margin-top: 8px;
                        }

                        .no-result {
                            display: none;
                            text-align: center;
                            padding: 25px;
                            background: white;
                            border-radius: 8px;
                            color: #777;
                            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
                        }


                        /* REPORT FORM */

                        .report-section {
                            width: 84%;
                            max-width: 900px;
                            margin: 0 auto;
                            padding: 45px 0;
                            min-height: calc(100vh - 69px);
                        }

                        .report-box {
                            background: white;
                            padding: 30px;
                            border-radius: 8px;
                            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
                        }

                        .form-grid {
                            display: grid;
                            grid-template-columns: 1fr 1fr;
                            gap: 18px;
                        }

                        .form-group {
                            display: flex;
                            flex-direction: column;
                            gap: 7px;
                        }

                        .form-group label {
                            font-weight: bold;
                            font-size: 14px;
                        }

                        .full-width {
                            grid-column: 1 / 3;
                        }

                        textarea {
                            resize: vertical;
                            min-height: 100px;
                        }

                        .submit-btn {
                            margin-top: 20px;
                            background: #263b5b;
                        }

                        .submit-btn:hover {
                            background: #1c2d46;
                        }


                        /* SUCCESS MESSAGE */

                        #message {
                            margin-top: 18px;
                            padding: 12px;
                            border-radius: 5px;
                            display: none;
                            background: #e6f4ea;
                            color: #236b35;
                        }


                        /* FOOTER */

                        footer {
                            background: #263b5b;
                            color: white;
                            text-align: center;
                            padding: 20px;
                            font-size: 14px;
                        }


                        /* MOBILE */

                        @media (max-width: 750px) {

                            nav {
                                flex-direction: column;
                                gap: 12px;
                            }

                            nav a {
                                margin-left: 10px;
                                margin-right: 10px;
                            }

                            .hero h1 {
                                font-size: 32px;
                            }

                            .items {
                                grid-template-columns: 1fr;
                            }

                            .form-grid {
                                grid-template-columns: 1fr;
                            }

                            .full-width {
                                grid-column: 1;
                            }

                        }

                    </style>

                </head>


                <body>


                    <!-- NAVBAR -->

                    <nav>

                        <div class="logo">
                            CampusFind
                        </div>

                        <div>

                            <a href="#"
                               onclick="showPage('home'); return false;">
                                Home
                            </a>

                            <a href="#"
                               onclick="showPage('find'); return false;">
                                Find Item
                            </a>

                            <a href="#"
                               onclick="showPage('report'); return false;">
                                Report Item
                            </a>

                        </div>

                    </nav>



                    <!-- HOME PAGE -->

                    <section class="page active" id="home">

                        <div class="hero">

                            <h1>
                                Find What You Lost
                            </h1>

                            <p>
                                A simple lost and found portal for students on campus.
                            </p>

                            <button onclick="showPage('report')">
                                Report an Item
                            </button>

                        </div>

                    </section>



                    <!-- FIND ITEM PAGE -->

                    <section class="page" id="find">

                        <div class="container">

                            <h2 class="section-title">
                                Find an Item
                            </h2>


                            <!-- SEARCH BOX -->

                            <div class="search-box">

                                <div class="search-row">

                                    <input
                                        type="text"
                                        id="searchInput"
                                        placeholder="Enter item name..."
                                    >


                                    <select id="searchCategory">

                                        <option value="">
                                            All Categories
                                        </option>

                                        <option value="Electronics">
                                            Electronics
                                        </option>

                                        <option value="Bags">
                                            Bags
                                        </option>

                                        <option value="Keys">
                                            Keys
                                        </option>

                                        <option value="Stationery">
                                            Stationery
                                        </option>

                                        <option value="Other">
                                            Other
                                        </option>

                                    </select>


                                    <button
                                        id="searchButton"
                                        onclick="searchItem()">

                                        Search

                                    </button>

                                </div>


                                <p id="searchResult"></p>

                            </div>



                            <!-- RECENT ITEMS -->

                            <h2 class="section-title">
                                Recently Reported Items
                            </h2>


                            <div class="items" id="itemsList">


                                <!-- BACKPACK -->

                                <div class="item-card"
                                     data-name="black backpack"
                                     data-category="Bags">

                                    <h3>
                                        🎒 Black Backpack
                                    </h3>

                                    <p>
                                        <strong>Location:</strong>
                                        Library
                                    </p>

                                    <p>
                                        <strong>Date:</strong>
                                        02 Oct 2026
                                    </p>

                                    <span class="tag">
                                        Lost
                                    </span>

                                </div>


                                <!-- MOBILE -->

                                <div class="item-card"
                                     data-name="mobile phone"
                                     data-category="Electronics">

                                    <h3>
                                        📱 Mobile Phone
                                    </h3>

                                    <p>
                                        <strong>Location:</strong>
                                        Canteen
                                    </p>

                                    <p>
                                        <strong>Date:</strong>
                                        01 Oct 2026
                                    </p>

                                    <span class="tag">
                                        Found
                                    </span>

                                </div>


                                <!-- KEYS -->

                                <div class="item-card"
                                     data-name="key set"
                                     data-category="Keys">

                                    <h3>
                                        🔑 Key Set
                                    </h3>

                                    <p>
                                        <strong>Location:</strong>
                                        Parking Area
                                    </p>

                                    <p>
                                        <strong>Date:</strong>
                                        30 Sep 2026
                                    </p>

                                    <span class="tag">
                                        Lost
                                    </span>

                                </div>


                            </div>


                            <div
                                class="no-result"
                                id="noResult">

                                No matching items found.

                            </div>


                        </div>

                    </section>



                    <!-- REPORT ITEM PAGE -->

                    <section class="page" id="report">

                        <div class="report-section">

                            <h2 class="section-title">
                                Report an Item
                            </h2>


                            <div class="report-box">

                                <form onsubmit="submitReport(event)">


                                    <div class="form-grid">


                                        <!-- ITEM NAME -->

                                        <div class="form-group">

                                            <label for="itemName">
                                                Item Name
                                            </label>

                                            <input
                                                type="text"
                                                id="itemName"
                                                placeholder="e.g. Blue Water Bottle"
                                                required
                                            >

                                        </div>


                                        <!-- CATEGORY -->

                                        <div class="form-group">

                                            <label for="itemCategory">
                                                Category
                                            </label>

                                            <select
                                                id="itemCategory"
                                                required>

                                                <option value="">
                                                    Select category
                                                </option>

                                                <option value="Electronics">
                                                    Electronics
                                                </option>

                                                <option value="Bags">
                                                    Bags
                                                </option>

                                                <option value="Keys">
                                                    Keys
                                                </option>

                                                <option value="Stationery">
                                                    Stationery
                                                </option>

                                                <option value="Other">
                                                    Other
                                                </option>

                                            </select>

                                        </div>


                                        <!-- LOCATION -->

                                        <div class="form-group">

                                            <label for="location">
                                                Location
                                            </label>

                                            <input
                                                type="text"
                                                id="location"
                                                placeholder="Where was it found/lost?"
                                                required
                                            >

                                        </div>


                                        <!-- STATUS -->

                                        <div class="form-group">

                                            <label for="status">
                                                Status
                                            </label>

                                            <select
                                                id="status"
                                                required>

                                                <option value="">
                                                    Select status
                                                </option>

                                                <option value="Lost">
                                                    Lost
                                                </option>

                                                <option value="Found">
                                                    Found
                                                </option>

                                            </select>

                                        </div>


                                        <!-- DESCRIPTION -->

                                        <div class="form-group full-width">

                                            <label for="description">
                                                Description
                                            </label>

                                            <textarea
                                                id="description"
                                                placeholder="Add a short description of the item..."
                                                required
                                            ></textarea>

                                        </div>


                                    </div>


                                    <button
                                        type="submit"
                                        class="submit-btn"
                                        id="submitReportButton">

                                        Submit Report

                                    </button>


                                    <div id="message"></div>


                                </form>

                            </div>

                        </div>

                    </section>



                    <!-- FOOTER -->

                    <footer>

                        CampusFind | College Lost & Found Portal

                    </footer>



                    <!-- JAVASCRIPT -->

                    <script>


                        /* PAGE SWITCHING */

                        function showPage(pageId) {

                            const pages =
                                document.querySelectorAll(".page");


                            pages.forEach(function(page) {

                                page.classList.remove("active");

                            });


                            const selectedPage =
                                document.getElementById(pageId);


                            selectedPage.classList.add("active");


                            window.scrollTo(0, 0);

                        }



                        /* SEARCH AND FILTER FUNCTION */

                        function searchItem() {

                            const searchText =
                                document
                                    .getElementById("searchInput")
                                    .value
                                    .toLowerCase()
                                    .trim();


                            const selectedCategory =
                                document
                                    .getElementById("searchCategory")
                                    .value;


                            const cards =
                                document.querySelectorAll(".item-card");


                            const result =
                                document.getElementById("searchResult");


                            const noResult =
                                document.getElementById("noResult");


                            let foundItems = 0;


                            cards.forEach(function(card) {

                                const itemName =
                                    card
                                        .getAttribute("data-name")
                                        .toLowerCase();


                                const itemCategory =
                                    card.getAttribute("data-category");


                                const nameMatches =
                                    searchText === "" ||
                                    itemName.includes(searchText);


                                const categoryMatches =
                                    selectedCategory === "" ||
                                    itemCategory === selectedCategory;


                                if (nameMatches && categoryMatches) {

                                    card.style.display = "block";

                                    foundItems++;

                                } else {

                                    card.style.display = "none";

                                }

                            });


                            /* NO RESULTS */

                            if (foundItems === 0) {

                                noResult.style.display = "block";

                                result.style.color = "#b33";

                                result.innerHTML =
                                    "No matching item found.";

                            } else {

                                noResult.style.display = "none";

                                result.style.color = "#236b35";

                                result.innerHTML =
                                    foundItems +
                                    " item(s) found.";

                            }

                        }



                        /* REPORT FUNCTION */

                        function submitReport(event) {

                            event.preventDefault();


                            const itemName =
                                document
                                    .getElementById("itemName")
                                    .value;


                            const reference =
                                "CF-" +
                                Math.floor(
                                    1000 + Math.random() * 9000
                                );


                            const message =
                                document.getElementById("message");


                            message.style.display = "block";


                            message.innerHTML =
                                "Report submitted successfully! " +
                                "Reference ID: <strong>" +
                                reference +
                                "</strong>";


                        }


                    </script>


                </body>

                </html>
                """;


        exchange.getResponseHeaders().set(
                "Content-Type",
                "text/html; charset=UTF-8"
        );


        byte[] bytes =
                response.getBytes(StandardCharsets.UTF_8);


        exchange.sendResponseHeaders(
                200,
                bytes.length
        );


        try (OutputStream output =
                     exchange.getResponseBody()) {

            output.write(bytes);

        }

    }

}