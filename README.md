# Day 1: Threads

Each of 1,000 threads adds the integers 1 through 1,000,000. The main thread waits for them and adds their results.

## Run on this Mac

Open Terminal in this project folder:

```sh
javac --release 19 src/Main.java
java -cp src Main
```

Expected total: `500000500000000`. Elapsed time varies. Java 21 is installed; `--release 19` checks compatibility with Java 19.

## Required history

1. Create 1,000 Thread objects in an array.
2. Configure and start each thread to sum 1 through 1,000,000.
3. Store each result in its own slot in a long array.
4. Join all worker threads before reading their results.
5. Add all results and print the grand total.
6. On `timer-branch`, add a timer and commit.
7. Add comments and documentation on that branch and commit.
8. Merge `timer-branch` into `main` and push both branches.

## Explain the code

- `new Thread(...)` creates a thread object; `start()` schedules it to run independently. Calling `run()` directly would execute on the calling thread.
- The lambda `() -> { ... }` supplies the Runnable work. Runnable's run() returns no value, so each worker stores its result in `totals[index]`.
- `final int index = i` gives each lambda a stable index. Java lambdas may capture local variables only when final or effectively final.
- Each worker uses a local total and a distinct array slot, avoiding concurrent updates to one shared total.
- All threads are started before any joins. `join()` makes the main thread wait for that worker and ensures its writes are visible afterward.
- `long` prevents overflow. Each partial sum is 500000500000, already too large for int.
- `nanoTime()` measures elapsed duration, not the date. Subtract start from end; divide by 1,000,000,000.0 to convert nanoseconds to seconds.
- More threads are not automatically faster: creating and scheduling 1,000 platform threads has overhead.

## Wording clarification

The Day 1 numbered requirements ask for sums, so this project preserves that behavior. It performs one billion additions but does not sum the unique range 1 through one billion. Day 2's review wording asks for a count; that separate project returns one billion.

This recreation was prepared on macOS with GitKraken and Java. It was not created in IntelliJ: the user's instruction explicitly waived IntelliJ. No claim is made otherwise.

## Verification

Compiled with javac --release 19 and executed on this Mac. Observed total: 500000500000000. All staged development versions also compile. Generated .class files are ignored by Git.
