import time
import psutil
import os


def merge_sort(arr):
    if len(arr) <= 1:
        return arr
    middle = len(arr) // 2
    left = merge_sort(arr[:middle])
    right = merge_sort(arr[middle:])
    
    return merge(left, right)


def merge(left, right):
    result = []
    i = j = 0
    while i < len(left) and j < len(right):
        if left[i] < right[j]:
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1
    result.extend(left[i:])
    result.extend(right[j:])
    return result


def read_and_sort_file(file_path):
    with open(file_path, 'r') as file:
        arr = [int(line.strip()) for line in file.readlines()]
    sorted_arr = merge_sort(arr)

    # Caminho do novo arquivo
    new_file_path = file_path.replace('arq.txt', 'arq-teste-ordenado-mergeSort-python.txt')

    # Escrever a lista ordenada no novo arquivo
    with open(new_file_path, 'w') as file:
        for item in sorted_arr:
            file.write(f"{item}\n")

    return sorted_arr, new_file_path


# Caminho para a pasta Python
python_folder = 'Python'

# Criar a pasta Python se ela não existir
if not os.path.exists(python_folder):
    os.makedirs(python_folder)

# Caminho para a subpasta Resultados dentro da pasta Python
resultados_folder = os.path.join(python_folder, 'Resultados')

# Criar a pasta Resultados dentro da pasta Python, se não existir
if not os.path.exists(resultados_folder):
    os.makedirs(resultados_folder)

file_path = 'arq.txt'

# Medir o uso de memória antes da execução
process = psutil.Process(os.getpid())
mem_before = process.memory_info().rss / 1024  # Convertendo bytes para KB

# Medir o tempo de início
start_time = time.perf_counter()

# Ordenar e salvar a lista ordenada
sorted_arr, sorted_file_path = read_and_sort_file(file_path)

# Medir o tempo de fim e calcular a diferença
end_time = time.perf_counter()
elapsed_time_ms = (end_time - start_time) * 1000  # Convertendo segundos para ms

# Medir o uso de memória após a execução e calcular a diferença
mem_after = process.memory_info().rss / 1024  # Convertendo bytes para KB
mem_used_kb = mem_after - mem_before

# Exibir os resultados no console
print(f"Arquivo ordenado salvo como: {sorted_file_path}")
print(f"Tempo de execução: {elapsed_time_ms:.2f} ms")
print(f"RAM utilizada: {mem_used_kb:.2f} KB")

# Salvar os resultados no arquivo dentro de Python/Resultados
result_file_path = os.path.join(resultados_folder, 'resultado-merge-python.txt')
with open(result_file_path, 'a') as result_file:
    result_file.write(
        f"Tempo de execução: {elapsed_time_ms:.2f} ms, RAM utilizada: {mem_used_kb:.2f} KB\n"
    )
