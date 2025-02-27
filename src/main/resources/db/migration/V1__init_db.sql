CREATE TABLE Worker (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(1000) NOT NULL CHECK (LENGTH(name)>=2 AND LENGTH(name)<=1000),
    birthday DATE CHECK EXTRACT(YEAR FROM birthday)>1900,
    level VARCHAR(10) NOT NULL CHECK ( level IN ('Trainee', 'Junior', 'Middle', 'Senior')),
    salary INT CHECK (salary>=100 AND salary<=100000)
);

CREATE TABLE client (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(1000) NOT NULL CHECK (LENGTH(name)>=2 AND LENGTH(name)<=1000)
);

CREATE TABLE project (
    id IDENTITY PRIMARY KEY,
    client_id INT,
    start_date DATE,
    finish_date DATE,
    FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE project_worker (
    project_id INT NOT NULL,
    worker_id INT NOT NULL,
    PRIMARY KEY (project_id, worker_id),
    FOREIGN KEY (project_id) REFERENCES project(id),
    FOREIGN KEY (worker_id) REFERENCES worker(id)
);
