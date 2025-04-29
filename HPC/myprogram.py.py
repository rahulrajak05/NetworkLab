import numpy as np
import time
import cupy as cp

# Number of elements in vectors
N = 1000000

# Generate random vectors
A = np.random.rand(N).astype(np.float32)
B = np.random.rand(N).astype(np.float32)

# CPU vector addition
def vector_add_cpu(A, B):
    return A + B

# Measure CPU execution time
start_cpu = time.time()
C_CPU = vector_add_cpu(A, B)
end_cpu = time.time()
time_cpu = end_cpu - start_cpu

# GPU vector addition using CuPy
def vector_add_gpu(A, B):
    A_gpu = cp.asarray(A)
    B_gpu = cp.asarray(B)
    
    start_gpu = cp.cuda.Event()
    stop_gpu = cp.cuda.Event()
    
    start_gpu.record()
    C_gpu = A_gpu + B_gpu
    stop_gpu.record()
    stop_gpu.synchronize()
    
    time_gpu = start_gpu.elapsed_time(stop_gpu)  # Corrected time measurement
    return C_gpu.get(), time_gpu

# Measure GPU execution time
C_GPU, time_gpu = vector_add_gpu(A, B)

# Print execution times
print(f"Time taken by CPU: {time_cpu:.6f} seconds")
print(f"Time taken by GPU: {time_gpu:.6f} milliseconds")
