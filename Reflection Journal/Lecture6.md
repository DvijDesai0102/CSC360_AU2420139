# Lecture 6: Progressive Drawing Complexity, Maven Lifecycle Architecture, Concurrency, and Java Runtime Mechanics

**Date:** 25 August 2026  
**Course:** CSC360: Computer Graphics and Interaction

---

### Procedural Geometry and the Three Tiers of Visual Complexity

The lecture established the structured roadmap for graphical rendering complexity that defines the practical development across our coursework. Computational computer graphics relies on breaking down natural and synthetic environments into structured mathematical abstractions. To master this translation, our coursework is divided into three progressive tiers of visual complexity: orthogonal primitive geometry, multi-point polygon triangulation, and recursive generative fractal structures.

The first tier begins at the baseline of two-dimensional coordinate geometry with the centered square. While seemingly straightforward, rendering a bounded orthogonal rectangle introduces the core principles of screen-space coordinate systems, viewport aspect ratios, bounding box arithmetic, and Cartesian-to-raster mapping. The top-left screen origin standard requires calculating precise half-width and half-height offsets to guarantee strict geometric alignment with the center of the drawing canvas.

The second tier advances beyond uniform four-sided structures into multi-point polygonal rendering, centered on triangle rasterization. Triangles serve as the universal geometric primitive across modern hardware pipelines because three non-collinear points unambiguously define a flat plane in space, avoiding non-planar surface ambiguities. This stage demands rigorous coordinate validation, requiring mathematical calculations such as vector cross products and determinant computations to verify non-collinearity, calculate barycentric coordinates, and apply affine transformations like rotation, scaling, and shear across arbitrary angular orientations.

The third and final tier culminates in procedural and recursive graphics, specifically generative fractal trees and botanical branching systems. Generating natural flora algorithmically moves past static shapes into dynamic, self-similar mathematics. By leveraging recursive function calls, Lindenmayer systems (L-systems), and parameterized branching angles, a simple root stem splits into sub-branches across successive recursion depths. Each branching level applies geometric scaling factors and rotational matrix transforms, simulating botanical growth patterns and demonstrating how concise algorithmic rules yield visually rich natural structures.

---

### Collaborative Version Control Hygiene and Upstream Synchronization

In professional software development and academic version control, maintaining repository synchronization is paramount. The lecture placed heavy emphasis on Git collaboration hygiene, specifically the non-negotiable protocol of always pulling upstream changes prior to initiating any new local development work. 

In collaborative settings, multiple team members commit changes, refactor shared classes, and introduce new assets to the centralized remote repository simultaneously. When a developer begins writing code on an outdated local branch without synchronizing first, they create a diverged branch history. This divergence leads to painful merge conflicts, silent functional regressions, and accidental overwrites of team contributions during pull requests. Executing a standard upstream pull or rebase workflow ensures that local feature branches are strictly anchored to the latest remote commit. This practice simplifies integration cycles, maintains a clean linear commit graph, and prevents race conditions during continuous integration builds.

---

### Build Automation Architecture and the Role of Maven in IntelliJ IDEA

As graphical software projects scale beyond trivial single-file scripts, managing compilation targets, third-party libraries, classpaths, and runtime packaging manually becomes impractical. The lecture explored the architecture of Apache Maven as a declarative build automation tool designed to eliminate these build management challenges.

At the core of every Maven-managed project lies the Project Object Model configuration file, known as `pom.xml`. This manifest acts as the central source of truth for the entire software build lifecycle. Rather than bundling raw binary archive files directly inside a version-controlled repository, the `pom.xml` specifies dependencies declaratively using unique coordinates consisting of a GroupId, ArtifactId, and Version. Maven automatically queries central and custom artifact repositories, resolves the complete transitive dependency tree, detects classpath version collisions, and caches required runtime packages locally on the host machine.

Beyond managing dependencies, the `pom.xml` coordinates project metadata, specifies the target Java Virtual Machine bytecode level, and configures build plugins for testing frameworks, bytecode packaging, and shader compilation utilities. We explored integrating Maven natively within the IntelliJ IDEA development environment, leveraging its graphical tool window to trigger standard build phases. Navigating phases such as `clean` (removing previously compiled target directories), `compile` (compiling raw source code into Java class files), `test` (executing automated unit suites), `package` (bundling compiled bytecode into deployable JAR archives), and `install` (publishing artifacts to the local cache) provides a standardized, repeatable compilation pipeline across different development machines.

---

### Concurrency Models, Operating System Fundamentals, and UI Thread Safety

A central component of the lecture examined the bridge between low-level operating system execution models and the runtime architecture of graphical user interfaces. We reviewed foundational operating system concepts, distinguishing between processes and threads to analyze system resource allocation.

A process represents an independent executing program instance that is allocated its own dedicated, isolated virtual memory space, file handles, security contexts, and system resources by the operating system kernel. Communication between discrete processes requires explicit inter-process communication mechanisms such as network sockets, pipes, or shared memory segments. In contrast, a thread represents the smallest schedulable unit of CPU execution within a parent process. All threads running within a common process share identical virtual address spaces, heap allocations, and open file descriptors, while maintaining their own private call stacks and program counters. This shared-memory model makes multi-threaded execution computationally efficient, but introduces severe synchronization challenges including race conditions, thread starvation, and deadlocks.

This concurrency foundation explains a core architectural rule of graphical frameworks: standard graphical user interface toolkits, including Java Swing, JavaFX, and native operating system UI frameworks, are fundamentally not thread-safe.

Thread safety implies that a shared resource or data structure can be accessed and modified concurrently by multiple execution threads without causing data corruption or operational instability. User interface component hierarchies, such as the Swing component tree and rendering pipelines, maintain deeply nested internal state models governing component bounds, visual styling, focus hierarchies, and event listener lists. If multiple background worker threads were permitted to read and mutate UI properties simultaneously without strict locking, asynchronous state mutations would corrupt graphics memory, cause partial redraws, throw concurrent modification exceptions, and create severe visual tearing on screen.

Implementing complete internal synchronization locks across every single UI method and property would introduce massive computational locking overhead, causing severe input latency, stuttering frame rates, and recurring thread deadlocks during rapid render loops. Consequently, GUI frameworks universally adopt a single-threaded execution model. In Java Swing, this single dedicated pipeline is known as the Event Dispatch Thread (EDT). The Event Dispatch Thread executes all graphical rendering instructions, processes repaint requests, dispatches mouse and keyboard events, and invokes component event listeners sequentially.

To prevent the user interface from freezing during resource-intensive computations such as complex geometric procedural generation, ray tracing calculations, or network communication, developers must offload heavy workloads to separate background worker threads. Once the background computation finishes, any resulting modifications to on-screen UI components must be scheduled back onto the Event Dispatch Thread asynchronously using utility methods like `SwingUtilities.invokeLater`. This ensures all UI state transitions occur in a strictly ordered, thread-safe sequence.

---

### Java Runtime Architecture and the Mechanics of Cold Starts

The lecture concluded with an analysis of Java execution mechanics, examining why Java programs experience a noticeable cold start delay and are rarely selected as lightweight command-line scripting languages.

Lightweight interpreted scripting languages such as Python, Bash, or Ruby parse and execute raw source files immediately line-by-line via an interpreter runtime with virtually zero startup latency. In contrast, running a Java application initiates a multi-stage JVM bootstrap process. When the Java Virtual Machine launches, it must initialize the runtime process, reserve virtual memory segments, load fundamental core system classes, verify compiled bytecode security constraints, link dynamic class references, and construct the execution context before reaching the `public static void main` entry point.

Once launched, the HotSpot JVM initially executes bytecode using an interpreter while continuously monitoring running code to identify hot spots, which are performance-critical loops and frequently invoked methods. The JVM dynamically invokes its Just-In-Time (JIT) compilers, Tier 1 (C1) for rapid baseline compilation and Tier 2 (C2) for deep, aggressive machine-code optimizations like method inlining, loop unrolling, and dead-code elimination. While this continuous dynamic profiling and native compilation enables long-running Java applications, graphics engines, and enterprise web servers to achieve near-native C++ performance levels, the initial bootstrap overhead creates a visible startup delay. This runtime cost makes Java less practical for brief, disposable scripting tasks, but highly effective for persistent graphical software and computational simulation platforms.

---

### Curated Resources and Further Reading

#### Architectural & Concurrency Articles
* Oracle Official Guide: Threads and Swing (Detailed breakdown of why Swing is single-threaded and how to properly utilize the Event Dispatch Thread).
* Martin Fowler: The Maven Build Lifecycle (Comprehensive structural guide explaining declarative build configurations and transitive dependency management).
* Graham King: What Every Programmer Should Know About Memory and Concurrency (Deep architectural explanation of processes, memory spaces, and threading).

#### Graphics & Mathematics Deep Dives
* Nature of Code by Daniel Shiffman: Chapter 8 on Fractals and Recursive Trees (Visual and algorithmic breakdown of L-systems, branch angles, and recursive generation).
* Scratchapixel: Geometric Foundations of 2D & 3D Triangle Rasterization (Mathematical analysis of barycentric coordinates, edge functions, and polygon rasterization).

#### Educational Video Lectures
* MIT OpenCourseWare: Introduction to Operating Systems, Processes, and Threads (In-depth university lecture covering process isolation, CPU scheduling, and synchronization).
* The Coding Train: Coding Challenge on Recursive Fractal Trees (Practical implementation of recursive graphical branching using coordinate transformations).
